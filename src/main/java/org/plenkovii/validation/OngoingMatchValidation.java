package org.plenkovii.validation;

import org.plenkovii.exception.InvalidUuidException;
import org.plenkovii.service.OngoingMatchesService;

import java.util.UUID;

public class OngoingMatchValidation {
    public static void validateUuid(OngoingMatchesService ongoingMatchesService, UUID uuid) {
        if (ongoingMatchesService.getMatch(uuid) == null) {
            throw new InvalidUuidException("Матч не найден");
        }
    }
}
