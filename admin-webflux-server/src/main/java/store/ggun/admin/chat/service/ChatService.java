package store.ggun.admin.chat.service;
import org.springframework.http.codec.ServerSentEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.admin.chat.domain.ChatDTO;
import store.ggun.admin.chat.domain.ChatModel;

public interface ChatService {

    Flux<ChatDTO> receiveByRoomId(String id);

    Mono<Boolean> save(ChatModel entity);

    Flux<ServerSentEvent<ChatDTO>> connect(String roomId);

}
