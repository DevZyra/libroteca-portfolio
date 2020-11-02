package pl.devzyra.services;

import org.springframework.stereotype.Service;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.model.entities.VerificationTokenEntity;
import pl.devzyra.repositories.VerificationTokenRepository;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;


    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationTokenEntity getByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationTokenEntity getByUser(UserEntity user) {
        return verificationTokenRepository.findByUserEntity(user);
    }

    @Override
    public VerificationTokenEntity save(UserEntity user, String token) {
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(user, token);
        verificationTokenEntity.setExpirationDate(calculateExpirationDate(24 * 60));

        return verificationTokenRepository.save(verificationTokenEntity);
    }

    private Timestamp calculateExpirationDate(int expTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
