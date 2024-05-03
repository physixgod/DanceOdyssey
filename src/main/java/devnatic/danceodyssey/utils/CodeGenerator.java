package devnatic.danceodyssey.utils;

import java.util.UUID;

public class CodeGenerator {
    public static String generateRandomCode() {
        return UUID.randomUUID().toString();
    }
}
