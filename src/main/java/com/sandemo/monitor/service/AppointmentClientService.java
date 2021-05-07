package com.sandemo.monitor.service;

import com.sandemo.monitor.dto.AppointmentResponseDto;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
public interface AppointmentClientService {

    AppointmentResponseDto callAndGetResponseForPincode(final Long pincode, final String date);

    AppointmentResponseDto callAndGetResponseForDistrict(final Long districtId, final String date);
}
