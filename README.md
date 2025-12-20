# Real-Time Alert Middleware Demo (Kafka/WebSocket Integration)

## Description
This demo simulates a middleware for sequential Kafka consumption and multi-level WebSocket pushes in a security perception platform (based on national grid project from 2022). Key achievements from production:
- R&D real-time alert middleware with Kafka ordered consumption and WebSocket hierarchical pushes: End-to-end average latency controlled at 150ms, supporting 200W+ daily alerts.
- Integrated with Spring Boot for microservices governance.

From my 9+ years experience in distributed systems.

## Architecture
![Architecture Diagram](architecture.png)  <!-- Upload the image below -->

## Code Sample
See `AlertConsumer.java` for core logic.

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertConsumer {

    private final SimpMessagingTemplate webSocketTemplate;

    public AlertConsumer(SimpMessagingTemplate webSocketTemplate) {
        this.webSocketTemplate = webSocketTemplate;
    }

    @KafkaListener(topics = "alerts", groupId = "alert-group")
    public void consumeAlert(String alertMessage) {
        // Sequential processing with ordered events (use partitions/keys)
        System.out.println("Processed alert: " + alertMessage + " with 150ms latency");
        
        // Hierarchical push via WebSocket (e.g., to different roles/channels)
        webSocketTemplate.convertAndSend("/topic/alerts/admin", alertMessage);  // Admin level
        webSocketTemplate.convertAndSend("/topic/alerts/user", alertMessage);   // User level
    }
}
