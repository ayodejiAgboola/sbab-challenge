package se.sbab.sbabchallenge.model;
import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LineNumber",
        "DirectionCode",
        "JourneyPatternPointNumber",
        "LastModifiedUtcDateTime",
        "ExistsFromDate"
})
public record LineStopsResult (
        @JsonProperty("LineNumber")
        Integer lineNumber,
        @JsonProperty("DirectionCode")
        String directionCode,
        @JsonProperty("JourneyPatternPointNumber")
        int journeyPatternPointNumber,
        @JsonProperty("LastModifiedUtcDateTime")
        String lastModifiedUtcDateTime,
        @JsonProperty("ExistsFromDate")
        String existsFromDate
){

}
