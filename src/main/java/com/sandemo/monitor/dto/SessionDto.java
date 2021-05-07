package com.sandemo.monitor.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SessionDto {

    private String sessionId;
    private String date;
    private Integer availableCapacity;
    private Integer minAgeLimit;
    private String vaccine;
    private List<String> slots;

    public SessionDto(@JsonProperty("session_id") String sessionId,
                      @JsonProperty("date") String date,
                      @JsonProperty("available_capacity") Integer availableCapacity,
                      @JsonProperty("min_age_limit") Integer minAgeLimit,
                      @JsonProperty("vaccine") String vaccine,
                      @JsonProperty("slots") List<String> slots) {

        this.sessionId = sessionId;
        this.date = date;
        this.availableCapacity = availableCapacity;
        this.minAgeLimit = minAgeLimit;
        this.vaccine = vaccine;
        this.slots = slots;
    }
}
