package io.applianceskeeper.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class MailController {

    private final MailService service;

    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendMail() throws MessagingException {

        return ResponseEntity.ok("wysłano");

    }


}
