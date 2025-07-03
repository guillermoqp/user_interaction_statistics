package co.com.bancolombia.usecase.utils;

import co.com.bancolombia.model.stats.Stats;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static String generateHash(Stats stats) {
        String raw = String.format("%d,%d,%d,%d,%d,%d,%d",
                stats.getTotalContactoClientes(),
                stats.getMotivoReclamo(),
                stats.getMotivoGarantia(),
                stats.getMotivoDuda(),
                stats.getMotivoCompra(),
                stats.getMotivoFelicitaciones(),
                stats.getMotivoCambio());

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(raw.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}