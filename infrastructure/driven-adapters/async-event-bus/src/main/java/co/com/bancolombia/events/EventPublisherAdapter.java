package co.com.bancolombia.events;

import co.com.bancolombia.model.stats.gateways.EventPublisher;
import co.com.bancolombia.model.stats.Stats;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventPublisherAdapter implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> publish(Stats stats) {
        return Mono.fromRunnable(() -> {
            try {
                String message = objectMapper.writeValueAsString(stats);
                rabbitTemplate.convertAndSend("event.stats.validated", "", message);
            } catch (Exception e) {
                throw new RuntimeException("Error publicando a RabbitMQ", e);
            }
        });
    }
}