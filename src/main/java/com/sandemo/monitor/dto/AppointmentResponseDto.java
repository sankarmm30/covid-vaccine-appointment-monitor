package com.sandemo.monitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/*
 * @author Sankar Manthiram
 * @created May - 2021
*/
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppointmentResponseDto {

    private List<CenterDto> centerDtoList;

    public AppointmentResponseDto(@JsonProperty("centers") List<CenterDto> centerDtoList) {

        this.centerDtoList = centerDtoList;
    }
}
