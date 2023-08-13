package se.sbab.sbabchallenge.service;

import okhttp3.*;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import se.sbab.sbabchallenge.model.LineStopsResult;
import se.sbab.sbabchallenge.model.SLResponseObject;
import se.sbab.sbabchallenge.model.StopsResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SLServiceTests {
    private final OkHttpClient client = mock(OkHttpClient.class);
    final Call remoteCall = mock(Call.class);
    private final String STOP_URL = "https://api.sl.se/api2/LineData.json?model=stop&key=5da196d47f8f4e5facdb68d2e25b9eae";
    private final String JOUR_URL = "https://api.sl.se/api2/LineData.json?model=jour&key=5da196d47f8f4e5facdb68d2e25b9eae&DefaultTransportModeCode=BUS";
    private final SLService slService = new SLService(client, JOUR_URL, STOP_URL);

    @Test
    public void shouldGetStopsAndMapOnRequest() throws IOException {
        String expectedStops = """
                {
                    "StatusCode": 0,
                    "Message": null,
                    "ExecutionTime": 0,
                    "ResponseData": {
                        "Version": "2023-08-11 00:09",
                        "Type": "StopPoint",
                        "Result": [
                            {
                                 "StopPointNumber": "10001",
                                 "StopPointName": "Stadshagsplan",
                                 "StopAreaNumber": "10001",
                                 "LocationNorthingCoordinate": "59.3373571967995",
                                 "LocationEastingCoordinate": "18.0214674159693",
                                 "ZoneShortName": "A",
                                 "StopAreaTypeCode": "BUSTERM",
                                 "LastModifiedUtcDateTime": "2022-10-28 00:00:00.000",
                                 "ExistsFromDate": "2022-10-28 00:00:00.000"
                             },
                             {
                                 "StopPointNumber": "10002",
                                 "StopPointName": "John Bergs plan",
                                 "StopAreaNumber": "10002",
                                 "LocationNorthingCoordinate": "59.3361450073188",
                                 "LocationEastingCoordinate": "18.0222866342593",
                                 "ZoneShortName": "A",
                                 "StopAreaTypeCode": "BUSTERM",
                                 "LastModifiedUtcDateTime": "2015-09-24 00:00:00.000",
                                 "ExistsFromDate": "2015-09-24 00:00:00.000"
                             }
                        ]
                    }
                }
                """;
        Request mockRequest = new Request.Builder().get()
                .url(STOP_URL)
                .addHeader("Accept-Encoding","gzip, deflate")
                .build();
        Response response = new Response.Builder().request(mockRequest).code(200).body(ResponseBody
                .create(MediaType.get("application/json; charset=utf-8"),expectedStops))
                .message("").protocol(Protocol.HTTP_2).build();
        when(remoteCall.execute()).thenReturn(response);
        when(client.newCall(any())).thenReturn(remoteCall);
        SLResponseObject<StopsResult> r = slService.getStops();
        assert r.responseData().result().size() == 2;
    }

    @Test
    public void shouldGetLineStopsAndMapOnRequest() throws IOException {
        String expectedJourneys = """
                {
                    "StatusCode": 0,
                    "Message": null,
                    "ExecutionTime": 0,
                    "ResponseData": {
                        "Version": "2023-08-11 00:09",
                        "Type": "JourneyPatternPointOnLine",
                        "Result": [
                            {
                                  "LineNumber": "1",
                                  "DirectionCode": "1",
                                  "JourneyPatternPointNumber": "10008",
                                  "LastModifiedUtcDateTime": "2022-02-15 00:00:00.000",
                                  "ExistsFromDate": "2022-02-15 00:00:00.000"
                              },
                              {
                                  "LineNumber": "1",
                                  "DirectionCode": "1",
                                  "JourneyPatternPointNumber": "10012",
                                  "LastModifiedUtcDateTime": "2023-03-07 00:00:00.000",
                                  "ExistsFromDate": "2023-03-07 00:00:00.000"
                              }
                        ]
                    }
                }
                """;
        Request mockRequest = new Request.Builder().get()
                .url(JOUR_URL)
                .addHeader("Accept-Encoding","gzip, deflate")
                .build();
        Response response = new Response.Builder().request(mockRequest).code(200).body(ResponseBody
                .create(MediaType.get("application/json; charset=utf-8"),expectedJourneys))
                .message("").protocol(Protocol.HTTP_2).build();
        when(remoteCall.execute()).thenReturn(response);
        when(client.newCall(any())).thenReturn(remoteCall);
        SLResponseObject<LineStopsResult> r = slService.getLineStops();
        assert r.responseData().result().size() == 2;
    }

    @Test
    public void shouldReturnTupleOfTop10AndListOfNames() throws IOException {
        String expectedJourneys = """
                {
                    "StatusCode": 0,
                    "Message": null,
                    "ExecutionTime": 0,
                    "ResponseData": {
                        "Version": "2023-08-11 00:09",
                        "Type": "JourneyPatternPointOnLine",
                        "Result": [
                            {
                                  "LineNumber": "1",
                                  "DirectionCode": "1",
                                  "JourneyPatternPointNumber": "10001",
                                  "LastModifiedUtcDateTime": "2022-02-15 00:00:00.000",
                                  "ExistsFromDate": "2022-02-15 00:00:00.000"
                              },
                              {
                                  "LineNumber": "1",
                                  "DirectionCode": "1",
                                  "JourneyPatternPointNumber": "10002",
                                  "LastModifiedUtcDateTime": "2023-03-07 00:00:00.000",
                                  "ExistsFromDate": "2023-03-07 00:00:00.000"
                              }
                        ]
                    }
                }
                """;
        String expectedStops = """
                {
                    "StatusCode": 0,
                    "Message": null,
                    "ExecutionTime": 0,
                    "ResponseData": {
                        "Version": "2023-08-11 00:09",
                        "Type": "StopPoint",
                        "Result": [
                            {
                                 "StopPointNumber": "10001",
                                 "StopPointName": "Stadshagsplan",
                                 "StopAreaNumber": "10001",
                                 "LocationNorthingCoordinate": "59.3373571967995",
                                 "LocationEastingCoordinate": "18.0214674159693",
                                 "ZoneShortName": "A",
                                 "StopAreaTypeCode": "BUSTERM",
                                 "LastModifiedUtcDateTime": "2022-10-28 00:00:00.000",
                                 "ExistsFromDate": "2022-10-28 00:00:00.000"
                             },
                             {
                                 "StopPointNumber": "10002",
                                 "StopPointName": "John Bergs plan",
                                 "StopAreaNumber": "10002",
                                 "LocationNorthingCoordinate": "59.3361450073188",
                                 "LocationEastingCoordinate": "18.0222866342593",
                                 "ZoneShortName": "A",
                                 "StopAreaTypeCode": "BUSTERM",
                                 "LastModifiedUtcDateTime": "2015-09-24 00:00:00.000",
                                 "ExistsFromDate": "2015-09-24 00:00:00.000"
                             }
                        ]
                    }
                }
                """;
        Request mockRequest = new Request.Builder().get()
                .url("https://api.sl.se/api2/LineData.json?model=line&key=5da196d47f8f4e5facdb68d2e25b9eae&DefaultTransportModeCode=BUS")
                .addHeader("Accept-Encoding","gzip, deflate")
                .build();
        Response mockJourResponse = new Response.Builder().request(mockRequest).code(200).body(ResponseBody
                .create(MediaType.get("application/json; charset=utf-8"),expectedJourneys))
                .message("").protocol(Protocol.HTTP_2).build();
        Response mockStopResponse = new Response.Builder().request(mockRequest).code(200).body(ResponseBody
                        .create(MediaType.get("application/json; charset=utf-8"),expectedStops))
                .message("").protocol(Protocol.HTTP_2).build();
        when(remoteCall.execute()).thenReturn(mockJourResponse)
                .thenReturn(mockStopResponse);
        when(client.newCall(any())).thenReturn(remoteCall);
        Pair<HashMap<Integer, Integer>, List<String>> result = slService.mapLinesToStops();
        assert result.getValue0().size()==1;
        assert result.getValue0().containsKey(1);
        assert result.getValue0().get(1).equals(2);
        assert result.getValue1().size()==2;
        assert result.getValue1().contains("Stadshagsplan");
        assert result.getValue1().contains("John Bergs plan");


    }

}
