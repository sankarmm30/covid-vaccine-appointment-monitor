package com.sandemo.monitor.service.impl;

import com.sandemo.monitor.dto.AppointmentResponseDto;
import com.sandemo.monitor.dto.MailDto;
import com.sandemo.monitor.dto.SessionDto;
import com.sandemo.monitor.service.AppointmentClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@Service("appointmentSchedulerService")
public class AppointmentSchedulerServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentSchedulerServiceImpl.class);

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private AppointmentClientService appointmentClientService;
    private AppointmentMailServiceImpl appointmentMailService;
    private Environment environment;

    private String date;
    private Long pincode;
    private Long districtId;
    private Integer age;

    public AppointmentSchedulerServiceImpl(AppointmentClientService appointmentClientService,
                                           AppointmentMailServiceImpl appointmentMailService,
                                           Environment environment) {

        this.appointmentClientService = appointmentClientService;
        this.appointmentMailService = appointmentMailService;
        this.environment = environment;
    }

    @PostConstruct
    private void init() {

        this.pincode = this.environment.getRequiredProperty("appointment.pincode", Long.class);
        this.districtId = this.environment.getRequiredProperty("appointment.district.id", Long.class);
        this.age = this.environment.getRequiredProperty("appointment.age", Integer.class);

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        this.date = localDate.plusDays(1).format(formatter);
    }

    @Scheduled(fixedRate = 10000)
    public void run() {

        findAppointment(this.appointmentClientService.callAndGetResponseForPincode(this.pincode, this.date));

        findAppointment(this.appointmentClientService.callAndGetResponseForDistrict(this.districtId, this.date));
    }

    private void findAppointment(final AppointmentResponseDto appointmentResponseDto) {

        StringBuilder stringBuilder = new StringBuilder();

        if(appointmentResponseDto != null) {

            appointmentResponseDto
                    .getCenterDtoList()
                    .forEach(centerDto -> {

                        List<SessionDto> sessionDtoList = centerDto.getSessionDtoList();

                        if (!sessionDtoList.isEmpty()) {

                            sessionDtoList
                                    .stream()
                                    .filter(sessionDto -> sessionDto.getMinAgeLimit().equals(this.age) && sessionDto.getAvailableCapacity() > 0)
                                    .forEach(sessionDto -> {

                                        stringBuilder.append("name: ");
                                        stringBuilder.append(centerDto.getName());
                                        stringBuilder.append(" , pincode: ");
                                        stringBuilder.append(centerDto.getPincode());
                                        stringBuilder.append(" , age: ");
                                        stringBuilder.append(sessionDto.getMinAgeLimit());
                                        stringBuilder.append(" , available: ");
                                        stringBuilder.append(sessionDto.getAvailableCapacity());
                                        stringBuilder.append(" , date: ");
                                        stringBuilder.append(sessionDto.getDate());
                                        stringBuilder.append(System.lineSeparator());

                                        LOG.info("name: {} , pincode: {}, Age: {}, Available: {}, date: {}",
                                                centerDto.getName(),
                                                centerDto.getPincode(),
                                                sessionDto.getMinAgeLimit(),
                                                sessionDto.getAvailableCapacity(),
                                                sessionDto.getDate());
                                    });
                        }
                    });
        }

        if(stringBuilder.length() > 0) {

            MailDto mailDto =
                    MailDto.builder()
                    .mailFrom("sankar.mm30@gmail.com")
                    .mailTo("sankar.mm30@gmail.com")
                    .mailSubject("URGENT: Vaccine Appointment Available for date: "+ this.date)
                    .mailContent(stringBuilder.toString())
                    .build();

            this.appointmentMailService.sendEmail(mailDto);
        }
    }
}
