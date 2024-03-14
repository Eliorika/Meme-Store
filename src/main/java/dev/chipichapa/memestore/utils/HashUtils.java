package dev.chipichapa.memestore.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtils {
    private static final int HASH_LENGTH = 5;

    public static String generateRandomHash() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[HASH_LENGTH];
            random.nextBytes(bytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при генерации случайных данных", e);
        }
    }
}
