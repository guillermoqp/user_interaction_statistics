package co.com.bancolombia.model.stats;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class Stats {
    private Integer totalContactoClientes;
    private Integer motivoReclamo;
    private Integer motivoGarantia;
    private Integer motivoDuda;
    private Integer motivoCompra;
    private Integer motivoFelicitaciones;
    private Integer motivoCambio;
    private String hash;
    private Instant timestamp;
}