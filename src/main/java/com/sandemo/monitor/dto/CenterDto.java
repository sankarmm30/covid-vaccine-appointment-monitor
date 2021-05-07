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
public class CenterDto {

    private Long centerId;
    private String name;
    private String address;
    private String stateName;
    private String districtName;
    private String blockName;
    private Long pincode;
    private Long latitude;
    private Long longitude;
    private String timeFrom;
    private String timeTo;
    private String feeType;
    private List<SessionDto> sessionDtoList;

    public CenterDto(@JsonProperty("center_id") Long centerId,
                     @JsonProperty("name") String name,
                     @JsonProperty("address") String address,
                     @JsonProperty("state_name") String stateName,
                     @JsonProperty("district_name") String districtName,
                     @JsonProperty("block_name") String blockName,
                     @JsonProperty("pincode") Long pincode,
                     @JsonProperty("lat") Long latitude,
                     @JsonProperty("long") Long longitude,
                     @JsonProperty("from") String timeFrom,
                     @JsonProperty("to") String timeTo,
                     @JsonProperty("fee_type") String feeType,
                     @JsonProperty("sessions") List<SessionDto> sessionDtoList) {

        this.centerId = centerId;
        this.name = name;
        this.address = address;
        this.stateName = stateName;
        this.districtName = districtName;
        this.blockName = blockName;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.feeType = feeType;
        this.sessionDtoList = sessionDtoList;
    }
}
