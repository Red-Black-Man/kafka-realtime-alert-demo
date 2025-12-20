# Real-Time Alert Middleware Demo (Kafka/WebSocket Integration)

## Description
This demo showcases a middleware for sequential Kafka consumption and multi-level WebSocket pushes in a high-volume security platform, based on a production national grid system. Key achievements:
- Reduced end-to-end latency to 150ms with ordered events.
- Handled 2M+ daily alerts with SLA 99.97%.
- Integrated with Spring Boot for microservices, supporting failover and elastic scaling.

From my 9+ years of experience in distributed systems (project from 2022).

## Architecture
![Architecture Diagram](architecture.png)  <!-- Upload the image below -->

## Code Sample
See `AlertConsumer.java` for the core logic.

```java
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
        // Sequential processing logic (ensure order with partitions or keys)
        System.out.println("Received alert: " + alertMessage);
        
        // Push to WebSocket (multi-level: e.g., admin/user channels)
        webSocketTemplate.convertAndSend("/topic/alerts", alertMessage);
        
        // Latency optimization: Use async processing if needed, but keep sequential
    }
}
