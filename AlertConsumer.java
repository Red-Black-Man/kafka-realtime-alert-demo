package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class AlertConsumer {
    private final SimpMessagingTemplate webSocketTemplate;

    public AlertConsumer(SimpMessagingTemplate webSocketTemplate) {
        this.webSocketTemplate = webSocketTemplate;
    }

    @KafkaListener(topics = "alerts", groupId = "alert-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeAlert(String alertMessage) {
        System.out.println("Received alert: " + alertMessage);
        webSocketTemplate.convertAndSend("/topic/alerts", alertMessage);
    }
}
