package store.ggun.admin.chat.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chats")
public class ChatModel {
    @Id
    private String id;
    private String roomId;
    private String msg;
    private String sender;
    private LocalDateTime createdAt;
}