package co.com.bancolombia.dynamodb;

import co.com.bancolombia.model.stats.Stats;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import java.time.Instant;

@DynamoDbBean
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsEntity {

    private Instant timestamp;
    private Integer totalContactoClientes;
    private Integer motivoReclamo;
    private Integer motivoGarantia;
    private Integer motivoDuda;
    private Integer motivoCompra;
    private Integer motivoFelicitaciones;
    private Integer motivoCambio;
    private String hash;

    @DynamoDbPartitionKey
    public Instant getTimestamp() {
        return timestamp;
    }

    public static StatsEntity fromDomain(Stats stats) {
        return StatsEntity.builder()
                .timestamp(stats.getTimestamp())
                .totalContactoClientes(stats.getTotalContactoClientes())
                .motivoReclamo(stats.getMotivoReclamo())
                .motivoGarantia(stats.getMotivoGarantia())
                .motivoDuda(stats.getMotivoDuda())
                .motivoCompra(stats.getMotivoCompra())
                .motivoFelicitaciones(stats.getMotivoFelicitaciones())
                .motivoCambio(stats.getMotivoCambio())
                .hash(stats.getHash())
                .build();
    }
}