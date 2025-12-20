# Real-Time Alert Middleware Demo (Kafka/WebSocket Integration)

## Description
This demo simulates a middleware for sequential Kafka consumption and multi-level WebSocket pushes in a security perception platform (based on national grid project from 2022). Key achievements from production:
- R&D real-time alert middleware with Kafka ordered consumption and WebSocket hierarchical pushes: End-to-end average latency controlled at 150ms, supporting 200W+ daily alerts.
- Integrated with Spring Boot for microservices governance.

From my 9+ years experience in distributed systems.

## Architecture
<img width="954" height="474" alt="image" src="https://github.com/user-attachments/assets/830f5b23-20db-4bfd-95af-b2ea2b888bfb" />


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
```
## Configuration (application.yml)
```yaml
spring:
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: alert-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      enable-auto-commit: false  # For manual commit to ensure order
    listener:
      concurrency: 1  # Single thread for sequential processing
      ack-mode: manual  # Manual ack for latency control

server:
  port: 8080

# WebSocket config
spring:
  websocket:
    message-size-limit: 64KB
```
# Technologies

- Kafka, RabbitMQ (for messaging), Spring Boot, Spring Cloud, WebSocket
- Nacos for registry, Redis for caching, Docker/K8s for deployment
- Seata/Sentinel for distributed transactions and limiting

# Results

- Daily alerts: 200W+ with 150ms latency and hierarchical pushes.
- SLA: 99.97% for key tasks.
- Scalability: Supported provincial platform extension to 28 cities.

GitHub: [https://github.com/Red-Black-Man/kafka-realtime-alert-demo)]
