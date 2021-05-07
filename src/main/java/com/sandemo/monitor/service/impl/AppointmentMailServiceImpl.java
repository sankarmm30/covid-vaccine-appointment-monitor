package com.sandemo.monitor.service.impl;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sandemo.monitor.dto.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@Service("appointmentMailService")
public class AppointmentMailServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(AppointmentSchedulerServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(final MailDto mailDto) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mailDto.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mailDto.getMailFrom(), "vaccine.appointment.com"));
            mimeMessageHelper.setTo(mailDto.getMailTo());
            mimeMessageHelper.setText(mailDto.getMailContent());

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (Exception ex) {

            LOG.error("Exception while sending mail: {}", ex);
        }
    }
}
