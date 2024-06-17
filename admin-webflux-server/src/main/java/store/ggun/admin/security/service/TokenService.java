package store.ggun.admin.security.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import store.ggun.admin.security.domain.TokenModel;
import store.ggun.admin.security.repository.TokenRepository;
import java.time.Instant;
import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveRefreshToken(String email, String refreshToken, long refreshTokenExpiration){

        TokenModel token = TokenModel.builder()
                .email(email)
                .refreshToken(refreshToken)
                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiration)))
                .build();

        log.info("Service - TokenModel token : {}",token);

        tokenRepository.save(token)
                .flatMap(i -> Mono.just(i.getRefreshToken())).subscribe();

    }
}
