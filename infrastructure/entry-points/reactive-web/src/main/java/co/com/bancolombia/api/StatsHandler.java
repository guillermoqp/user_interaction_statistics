package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.StatsRequestDTO;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.stats.ValidateAndProcessStatsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class StatsHandler {
    private final ValidateAndProcessStatsUseCase useCase;

    public Mono<ServerResponse> handlePostStats(ServerRequest request) {
        return request.bodyToMono(StatsRequestDTO.class)
                .map(this::toDomain)
                .flatMap(useCase::execute)
                .then(ServerResponse.ok().build())
                .onErrorResume(e ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("{\"error\":\"" + e.getMessage() + "\"}")
                );
    }

    private Stats toDomain(StatsRequestDTO dto) {
        return Stats.builder()
                .totalContactoClientes(dto.getTotalContactoClientes())
                .motivoReclamo(dto.getMotivoReclamo())
                .motivoGarantia(dto.getMotivoGarantia())
                .motivoDuda(dto.getMotivoDuda())
                .motivoCompra(dto.getMotivoCompra())
                .motivoFelicitaciones(dto.getMotivoFelicitaciones())
                .motivoCambio(dto.getMotivoCambio())
                .hash(dto.getHash())
                .timestamp(Instant.now())
                .build();
    }
}