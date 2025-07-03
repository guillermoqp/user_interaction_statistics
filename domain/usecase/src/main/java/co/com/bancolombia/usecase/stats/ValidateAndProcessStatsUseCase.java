package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface ValidateAndProcessStatsUseCase {
    Mono<Void> execute(Stats stats);
}