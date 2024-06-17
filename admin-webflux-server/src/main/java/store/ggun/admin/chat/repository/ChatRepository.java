package store.ggun.admin.chat.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import store.ggun.admin.chat.domain.ChatModel;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<ChatModel, String>{
    // @Tailable
    Flux<ChatModel> findByRoomId(String roomId);
}
