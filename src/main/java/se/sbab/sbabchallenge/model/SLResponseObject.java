package se.sbab.sbabchallenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "StatusCode",
        "Message",
        "ExecutionTime",
        "ResponseData"
})
public record SLResponseObject<T> (
        @JsonProperty("StatusCode")
        Integer statusCode,
        @JsonProperty("Message")
        Object message,
        @JsonProperty("ExecutionTime")
        Integer executionTime,
        @JsonProperty("ResponseData")
        ResponseData<T> responseData
){

}
