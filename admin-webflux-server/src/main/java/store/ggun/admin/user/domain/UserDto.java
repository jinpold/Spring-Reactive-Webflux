package store.ggun.admin.user.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String userId;
    private String lastName;
    // private String password; 프론트로 보내는 값은 비번을 지운다
    private String firstName;
    private String email;
    // private List<RoleModel> roles;
}