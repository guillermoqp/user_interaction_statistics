package co.com.bancolombia.events;

import co.com.bancolombia.model.stats.Stats;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.test.StepVerifier;
import java.time.Instant;
import java.util.logging.Level;

import com.fasterxml.jackson.core.JsonProcessingException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class EventPublisherAdapterTest {

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private EventPublisherAdapter adapter;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        objectMapper = new ObjectMapper();
        adapter = new EventPublisherAdapter(rabbitTemplate, objectMapper);
    }

    @Test
    void shouldPublishEventSuccessfully() {
        Stats stats = Stats.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .hash("02946f262f2eb0d8d5c8e76c50433ed8")
                .timestamp(Instant.now())
                .build();

        StepVerifier.create(adapter.publish(stats))
                .verifyComplete();

        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate).convertAndSend(eq("event.stats.validated"), eq(""), messageCaptor.capture());

        // Verificamos que el mensaje sea JSON válido
        String sentMessage = messageCaptor.getValue();
        try {
        Stats deserialized = objectMapper.readValue(sentMessage, Stats.class);
        assertEquals(stats.getHash(), deserialized.getHash());
        assertEquals(stats.getTotalContactoClientes(), deserialized.getTotalContactoClientes());
        } catch (JsonProcessingException e) {
            Assertions.fail("Error al deserializar el mensaje: " + e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionOnSerializationError() {
        // Simula un objeto no serializable (ObjectMapper lanzará excepción)
        Stats invalid = mock(Stats.class);
        when(invalid.getTotalContactoClientes()).thenThrow(new RuntimeException("Error interno"));

        EventPublisherAdapter adapterWithRealMapper = new EventPublisherAdapter(rabbitTemplate, new ObjectMapper());

        StepVerifier.create(adapterWithRealMapper.publish(invalid))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().contains("Error publicando a RabbitMQ")
                ).verify();
    }
}