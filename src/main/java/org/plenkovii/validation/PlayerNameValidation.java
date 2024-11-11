package org.plenkovii.validation;

import java.security.InvalidParameterException;

public class PlayerNameValidation {
    public static void validate(String player1Name, String player2Name) {
        System.out.println("Validating...");
        if (player1Name == null || player1Name.isBlank()) {
            throw new InvalidParameterException("Пустое значение имени первого игрока");
        } else if (player2Name == null || player2Name.isBlank()) {
            throw new InvalidParameterException("Пустое значение имени второго игрока");
        } else if (player1Name.equals(player2Name)) {
            System.out.println("Введены два одинаковых имени");
            throw new InvalidParameterException("Введены два одинаковых имени");
        }
    }
}
