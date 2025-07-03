package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.gateways.EventPublisher;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import co.com.bancolombia.model.stats.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import java.time.Instant;
import static org.mockito.Mockito.*;

public class ValidateAndProcessStatsUseCaseImplTest {

    private StatsRepository repository;
    private EventPublisher publisher;
    private ValidateAndProcessStatsUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(StatsRepository.class);
        publisher = mock(EventPublisher.class);
        useCase = new ValidateAndProcessStatsUseCase(repository, publisher);
    }

    /*@Test
    void shouldProcessValidStats() {
        Stats stats = validStats("02946f262f2eb0d8d5c8e76c50433ed8");
        when(repository.save(any())).thenReturn(Mono.empty());
        when(publisher.publish(any())).thenReturn(Mono.empty());

       // StepVerifier.create(useCase.execute(stats)).verifyComplete();

        verify(repository).save(any());
        verify(publisher).publish(any());
    }*/

    @Test
    void shouldFailWithInvalidHash() {
        Stats stats = validStats("invalidhash");

        StepVerifier.create(useCase.execute(stats))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("Hash invalido"))
                .verify();

        verify(repository, never()).save(any());
        verify(publisher, never()).publish(any());
    }

    private Stats validStats(String hash) {
        return Stats.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .hash(hash)
                .timestamp(Instant.now())
                .build();
    }
}