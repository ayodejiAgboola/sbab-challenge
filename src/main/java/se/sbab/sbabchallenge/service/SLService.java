package se.sbab.sbabchallenge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.sbab.sbabchallenge.model.LineStopsResult;
import se.sbab.sbabchallenge.model.SLResponseObject;
import se.sbab.sbabchallenge.model.StopsResult;

import java.io.IOException;
import java.util.*;

@Service
public class SLService {
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String JOUR_URL;
    private final String STOP_URL;

    public SLService(OkHttpClient client, @Value("${url.jour}") String jour, @Value("${url.stop}")String stop) {
        this.client = client;
        JOUR_URL = jour;
        STOP_URL = stop;
    }

    public Pair<HashMap<Integer, Integer>, List<String>> getSortedMapAndStopNames(){
        //Create map of line to list of stops
        HashMap <Integer, List<LineStopsResult>> lineStopsMap = new HashMap<>();
        SLResponseObject<LineStopsResult> lineStopsResult = getLineStops();
        List<LineStopsResult> lineStopList = lineStopsResult.responseData().result();
        for (LineStopsResult lineStopResult: lineStopList){
            if(lineStopsMap.containsKey(lineStopResult.lineNumber())){
                List<LineStopsResult>  temp = lineStopsMap.get(lineStopResult.lineNumber());
                temp.add(lineStopResult);
                lineStopsMap.put(lineStopResult.lineNumber(), temp);
            }else {
                lineStopsMap.put(lineStopResult.lineNumber(), new ArrayList<>(Arrays.asList(lineStopResult)));
            }
        }
        //Sort the map
        HashMap<Integer, Integer> sortedMap = sortMapByValues(lineStopsMap);
        Map.Entry<Integer, Integer> first = sortedMap.entrySet().iterator().next();
        //Fetch names for line with highest stops
        List<String> stopNames = getStopNames(lineStopsMap.get(first.getKey()));
        //Return tuple with sorted and filtered map and list of names
        return Pair.with(sortedMap,stopNames);
    }

    private List<String> getStopNames(List<LineStopsResult> lineStopList){
        SLResponseObject<StopsResult> stops = getStops();
        List<StopsResult> stopsList = stops.responseData().result();
        List<StopsResult> validStops = stopsList.stream().filter(v->lineStopList.stream()
                .anyMatch(k->v.stopPointNumber().equals(k.journeyPatternPointNumber()))).toList();

        return validStops.stream().map(StopsResult::stopPointName).toList();
    }

    private HashMap<Integer, Integer> sortMapByValues(HashMap<Integer, List<LineStopsResult>> mapToSort){
        List<Map.Entry<Integer, List<LineStopsResult>>> entryList = new LinkedList<>(mapToSort.entrySet());
        entryList.sort((t1, t2) -> t2.getValue().size() - t1.getValue().size());
        HashMap<Integer, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<LineStopsResult>> entry:entryList) {
            result.put(entry.getKey(),entry.getValue().size());
        }
        return result;
    }

    public SLResponseObject<LineStopsResult> getLineStops(){
        Request req = new Request.Builder().get().url(JOUR_URL).addHeader("Accept-Encoding","gzip, deflate").build();
        try (Response response = client.newCall(req).execute()){
            assert response.body() != null;
            return objectMapper.readValue(response.body().string(), new TypeReference<>() {});
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public SLResponseObject<StopsResult> getStops(){
        Request req = new Request.Builder().get().url(STOP_URL).addHeader("Accept-Encoding","gzip, deflate").build();
        try (Response response = client.newCall(req).execute()){
            assert response.body() != null;
            return objectMapper.readValue(response.body().string(), new TypeReference<>() {});
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
