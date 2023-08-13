package se.sbab.sbabchallenge.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Version",
        "Type",
        "Result"
})
public record ResponseData<T> (
        @JsonProperty("Version")
        String version,
        @JsonProperty("Type")
        String type,
        @JsonProperty("Result")
        List<T> result
){

}
