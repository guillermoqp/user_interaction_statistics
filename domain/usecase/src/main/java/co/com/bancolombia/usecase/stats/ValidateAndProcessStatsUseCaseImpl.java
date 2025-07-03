package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.StatsRepository;
import co.com.bancolombia.model.stats.gateways.EventPublisher;
import co.com.bancolombia.usecase.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.time.Instant;

@RequiredArgsConstructor
public class ValidateAndProcessStatsUseCaseImpl implements ValidateAndProcessStatsUseCase {

    private final StatsRepository repository;
    private final EventPublisher publisher;

    @Override
    public Mono<Void> execute(Stats stats) {
        String expectedHash = HashUtils.generateHash(stats);

        if (!expectedHash.equals(stats.getHash())) {
            return Mono.error(new IllegalArgumentException("Hash inválido"));
        }

        Stats statsWithTimestamp = Stats.builder()
                .totalContactoClientes(stats.getTotalContactoClientes())
                .motivoReclamo(stats.getMotivoReclamo())
                .motivoGarantia(stats.getMotivoGarantia())
                .motivoDuda(stats.getMotivoDuda())
                .motivoCompra(stats.getMotivoCompra())
                .motivoFelicitaciones(stats.getMotivoFelicitaciones())
                .motivoCambio(stats.getMotivoCambio())
                .hash(stats.getHash())
                .timestamp(Instant.now())
                .build();

        return repository.save(statsWithTimestamp)
                .then(publisher.publish(statsWithTimestamp));
    }
}
