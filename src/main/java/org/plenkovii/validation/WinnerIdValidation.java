package org.plenkovii.validation;

import org.plenkovii.exception.InvalidWinnerIdException;

public class WinnerIdValidation {
    public static void validate(String winnerId) {
        if (!winnerId.equals("1") && !winnerId.equals("2")) {
            throw new InvalidWinnerIdException("Получен неверный id игрока, выигравшего очко");
        }
    }
}
