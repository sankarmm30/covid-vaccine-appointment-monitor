package com.sandemo.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Sankar M <sankar.mm30@gmail.com>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailDto {

    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contentType;
    private List<Object> attachments;
}
