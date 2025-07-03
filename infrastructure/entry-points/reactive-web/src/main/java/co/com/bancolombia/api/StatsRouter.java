package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class StatsRouter {

    @Bean
    public RouterFunction<?> statsRoutes(StatsHandler handler) {
        return RouterFunctions
                .route(POST("/stats").and(accept(MediaType.APPLICATION_JSON)), handler::handlePostStats);
    }
}