package com.sandemo.monitor.service.impl;

import com.sandemo.monitor.dto.AppointmentResponseDto;
import com.sandemo.monitor.service.AppointmentClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@Service("appointmentClientService")
public class AppointmentClientServiceImpl implements AppointmentClientService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentClientServiceImpl.class);

    private static final String CACHE_CONTROL = "private, no-store, max-age=0";
    private static final String DISTRICT_ID = "calendarByDistrict?district_id=";
    private static final String PINCODE = "calendarByPin?pincode=";
    private static final String DATE = "&date=";
    private static final Integer CACHE_EXPIRES = 0;

    private RestTemplate restTemplate;
    private Environment environment;

    private String baseUrl;

    public AppointmentClientServiceImpl(RestTemplate restTemplate, Environment environment) {

        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @PostConstruct
    private void init() {

        this.baseUrl = this.environment.getRequiredProperty("appointment.baseurl");
    }

    /**
     * This method in charge of calling Appointment API and get the response for the given pincode
     *
     * @param pincode
     * @param date
     *
     * @return
     */
    @Override
    public AppointmentResponseDto callAndGetResponseForPincode(final Long pincode, final String date) {


        try{

            LOG.debug("Calling appointment api for pincode: {} ", pincode);

            final ResponseEntity<AppointmentResponseDto> responseEntity =
                    this.restTemplate.exchange(this.baseUrl + PINCODE + pincode + DATE + date,
                            HttpMethod.GET, buildHttpRequest(), AppointmentResponseDto.class);

            if(HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {

                return responseEntity.getBody();
            }

        } catch (Exception exception) {

            LOG.error("Exception while calling Appointment API: {}", exception);

            return null;
        }

        return null;
    }

    /**
     * This method in charge of calling Appointment API and get the response for the given district
     *
     * @param districtId
     * @param date
     *
     * @return
     */
    @Override
    public AppointmentResponseDto callAndGetResponseForDistrict(final Long districtId, final String date) {


        try{

            LOG.debug("Calling appointment api for district: {} ", districtId);

            final ResponseEntity<AppointmentResponseDto> responseEntity =
                    this.restTemplate.exchange(this.baseUrl + DISTRICT_ID + districtId + DATE + date,
                            HttpMethod.GET, buildHttpRequest(), AppointmentResponseDto.class);

            if(HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {

                return responseEntity.getBody();
            }

        } catch (Exception exception) {

            LOG.error("Exception while calling Appointment API: {}", exception);

            return null;
        }

        return null;
    }

    private HttpEntity<?> buildHttpRequest() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setExpires(CACHE_EXPIRES);
        httpHeaders.setCacheControl(CACHE_CONTROL);

        return new HttpEntity<>(httpHeaders);
    }
}
