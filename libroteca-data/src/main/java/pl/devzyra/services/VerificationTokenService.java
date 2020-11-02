package pl.devzyra.services;

import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.entities.VerificationTokenEntity;

public interface VerificationTokenService {

    VerificationTokenEntity getByToken(String token);

    VerificationTokenEntity getByUser(UserEntity user);

    VerificationTokenEntity save(UserEntity user, String token);
}
