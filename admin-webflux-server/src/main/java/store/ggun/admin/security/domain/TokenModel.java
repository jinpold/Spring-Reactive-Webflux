package store.ggun.admin.security.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document("tokens")
public class TokenModel {

    @Id
    private String tokenId;
    private String refreshToken;
    private String email;
    private Date expiration;

}
