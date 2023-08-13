package se.sbab.sbabchallenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LineNumber",
        "LineDesignation",
        "DefaultTransportMode",
        "DefaultTransportModeCode",
        "LastModifiedUtcDateTime",
        "ExistsFromDate"
})
public record LineResult (
        @JsonProperty("LineNumber")
        Integer lineNumber,
        @JsonProperty("LineDesignation")
        String lineDesignation,
        @JsonProperty("DefaultTransportMode")
        String defaultTransportMode,
        @JsonProperty("DefaultTransportModeCode")
        String defaultTransportModeCode,
        @JsonProperty("LastModifiedUtcDateTime")
        String lastModifiedUtcDateTime,
        @JsonProperty("ExistsFromDate")
        String existsFromDate) {
    

}
