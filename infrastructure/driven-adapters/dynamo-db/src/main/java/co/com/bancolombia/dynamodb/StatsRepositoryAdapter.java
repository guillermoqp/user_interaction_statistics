package co.com.bancolombia.dynamodb;

import co.com.bancolombia.model.stats.gateways.StatsRepository;
import co.com.bancolombia.model.stats.Stats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class StatsRepositoryAdapter implements StatsRepository {

    @Autowired
    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public Mono<Void> save(Stats stats) {
        var table = DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient)
                .build()
                .table("stats_table", TableSchema.fromBean(StatsEntity.class));

        StatsEntity entity = StatsEntity.fromDomain(stats);
        CompletableFuture<Void> saveFuture = table.putItem(entity).thenAccept(ignored -> {});
        return Mono.fromFuture(saveFuture);
    }
}