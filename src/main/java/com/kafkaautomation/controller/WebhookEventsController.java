package com.kafkaautomation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.kafkaautomation.domain.WebhookEvent;
import com.kafkaautomation.domain.Webhook;
import com.kafkaautomation.domain.WebhookEventType;
//import com.kafkaautomation.producer.WebhookEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebhookEventsController {

//    @Autowired
//    WebhookEventProducer webhookEventProducer;

    @PostMapping("/logpoint-webhook/alert/notification")
    public ResponseEntity<Webhook> postWebhookEvent(@RequestBody Webhook webhook) throws JsonProcessingException {
        //Invoke Kafka Producer
//        webhookEvent.setWebhookEventType(WebhookEventType.NEW);

        //webhookEventProducer.sendWebhookEvent_Approach2(webhookEvent);
        //return ResponseEntity.status(HttpStatus.CREATED).body(webhookEvent);
        return ResponseEntity.status(HttpStatus.OK).body(webhook);
    }

    //PUT
}
