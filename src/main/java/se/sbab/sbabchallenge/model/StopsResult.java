package se.sbab.sbabchallenge.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "StopPointNumber",
        "StopPointName",
        "StopAreaNumber",
        "LocationNorthingCoordinate",
        "LocationEastingCoordinate",
        "ZoneShortName",
        "StopAreaTypeCode",
        "LastModifiedUtcDateTime",
        "ExistsFromDate"
})
public record StopsResult (
        @JsonProperty("StopPointNumber")
        Integer stopPointNumber,
        @JsonProperty("StopPointName")
        String stopPointName,
        @JsonProperty("StopAreaNumber")
        String stopAreaNumber,
        @JsonProperty("LocationNorthingCoordinate")
        String locationNorthingCoordinate,
        @JsonProperty("LocationEastingCoordinate")
        String locationEastingCoordinate,
        @JsonProperty("ZoneShortName")
        String zoneShortName,
        @JsonProperty("StopAreaTypeCode")
        String stopAreaTypeCode,
        @JsonProperty("LastModifiedUtcDateTime")
        String lastModifiedUtcDateTime,
        @JsonProperty("ExistsFromDate")
        String existsFromDate
){

}
