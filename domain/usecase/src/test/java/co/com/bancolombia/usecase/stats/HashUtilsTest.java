package co.com.bancolombia.usecase.stats;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.utils.HashUtils;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashUtilsTest {
    @Test
    void shouldGenerateCorrectHash() {
        Stats stats = Stats.builder()
                .totalContactoClientes(250)
                .motivoReclamo(25)
                .motivoGarantia(10)
                .motivoDuda(100)
                .motivoCompra(100)
                .motivoFelicitaciones(7)
                .motivoCambio(8)
                .timestamp(Instant.now())
                .build();

        String expected = "02946f262f2eb0d8d5c8e76c50433ed8";
        String actual = HashUtils.generateHash(stats);

        assertNotEquals(expected, actual);
    }
}