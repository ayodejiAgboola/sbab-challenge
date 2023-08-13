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
    private final String LINE_URL="https://api.sl.se/api2/LineData.json?model=line&key=5da196d47f8f4e5facdb68d2e25b9eae&DefaultTransportModeCode=BUS";
    private final String JOUR_URL="https://api.sl.se/api2/LineData.json?model=jour&key=5da196d47f8f4e5facdb68d2e25b9eae&DefaultTransportModeCode=BUS";
    private final String STOP_URL="https://api.sl.se/api2/LineData.json?model=stop&key=5da196d47f8f4e5facdb68d2e25b9eae";

    public SLService(OkHttpClient client) {
        this.client = client;
    }

    public Pair<HashMap<Integer, Integer>, List<String>> mapLinesToStops(){
        HashMap <Integer, List<LineStopsResult>> lineStopsMap = new HashMap<>();
        SLResponseObject<LineStopsResult> lineStopsResult = getLineStops();
        assert lineStopsResult != null;
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
        HashMap<Integer, Integer> sortedMap = sortMapByValues(lineStopsMap);
        int index = 10;
        List<String> stopNames = new ArrayList<>();
        HashMap<Integer, Integer> top10Map = new HashMap<>();
        Iterator<Map.Entry<Integer, Integer>> iterator = sortedMap.entrySet().iterator();
        while (iterator.hasNext()&&index>0){
            Map.Entry<Integer, Integer> entry = iterator.next();
            top10Map.put(entry.getKey(),entry.getValue());
            //Fetch names for line with highest stops
            if(index == 10)
                stopNames = getStopNames(lineStopsMap.get(entry.getKey()));
            index --;
        }
        //Return tuple with sorted and filtered map and list of names
        return Pair.with(top10Map,stopNames);
    }

    private List<String> getStopNames(List<LineStopsResult> lineStopList){
        SLResponseObject<StopsResult> stops = getStops();
        assert stops != null;
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
            return objectMapper.readValue(response.body().string(), new TypeReference<>() {});
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public SLResponseObject<StopsResult> getStops(){
        Request req = new Request.Builder().get().url(STOP_URL).addHeader("Accept-Encoding","gzip, deflate").build();
        try (Response response = client.newCall(req).execute()){
            return objectMapper.readValue(response.body().string(), new TypeReference<>() {});
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
