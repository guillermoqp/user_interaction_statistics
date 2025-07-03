package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.StatsRequestDTO;
import co.com.bancolombia.usecase.stats.ValidateAndProcessStatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

@WebFluxTest(controllers = StatsRouter.class)
@Import(StatsHandler.class)
public class StatsHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ValidateAndProcessStatsUseCase useCase;
    private final String validHash = "02946f262f2eb0d8d5c8e76c50433ed8";

    @BeforeEach
    void setUp() {
        when(useCase.execute(any())).thenReturn(Mono.empty());
    }

    @Test
    void shouldReturn200ForValidStats() {
        StatsRequestDTO request = new StatsRequestDTO();
        request.setTotalContactoClientes(250);
        request.setMotivoReclamo(25);
        request.setMotivoGarantia(10);
        request.setMotivoDuda(100);
        request.setMotivoCompra(100);
        request.setMotivoFelicitaciones(7);
        request.setMotivoCambio(8);
        request.setHash(validHash);

        webTestClient.post()
                .uri("/stats")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturn400WhenUseCaseFails() {
        when(useCase.execute(any())).thenReturn(Mono.error(new IllegalArgumentException("Hash inválido")));

        StatsRequestDTO request = new StatsRequestDTO();
        request.setTotalContactoClientes(250);
        request.setMotivoReclamo(25);
        request.setMotivoGarantia(10);
        request.setMotivoDuda(100);
        request.setMotivoCompra(100);
        request.setMotivoFelicitaciones(7);
        request.setMotivoCambio(8);
        request.setHash("invalid");

        webTestClient.post()
                .uri("/stats")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }
}