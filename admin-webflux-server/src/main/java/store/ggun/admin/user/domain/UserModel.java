package store.ggun.admin.user.domain;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "userId")
@Document(collection = "users")
public class UserModel {

    @Id String userId ;
    String firstName ;
    String lastName ;
    String email;
    String password ;

//    List <RoleModel> roles ;

}