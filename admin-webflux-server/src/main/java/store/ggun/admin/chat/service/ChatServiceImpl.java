package store.ggun.admin.chat.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import store.ggun.admin.chat.domain.ChatDTO;
import store.ggun.admin.chat.domain.ChatModel;
import store.ggun.admin.chat.repository.ChatRepository;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final Map<String, Sinks.Many<ServerSentEvent<ChatDTO>>> sinks = new HashMap<>();

    @Override
    public Flux<ChatDTO> receiveByRoomId(String id) {
        return null;
    }

    @Override
    public Mono<Boolean> save(ChatModel entity) {
        return Mono.just(entity)
                .flatMap(i -> {
                    i.setCreatedAt(LocalDateTime.now());
                    return Mono.just(i);
                })
                .flatMap(i -> chatRepository.save(i))
                .doOnNext(i -> {
                    if(sinks.containsKey(i.getRoomId())){
                        sinks.get(i.getRoomId()).tryEmitNext(ServerSentEvent.builder(ChatDTO.builder()
                                .id(i.getId())
                                .roomId(i.getRoomId())
                                .msg(i.getMsg())
                                .sender(i.getSender())
                                .createdAt(i.getCreatedAt())
                                .build()).build());
                    }
                })
                .flatMap(i -> Mono.just(Boolean.TRUE))
                .switchIfEmpty(Mono.just(Boolean.FALSE));
    }

    public Flux<ServerSentEvent<ChatDTO>> connect(String roomId){
        if(sinks.containsKey(roomId))
            return sinks.get(roomId).asFlux();
        sinks.put(roomId, Sinks.many().multicast().onBackpressureBuffer());
        chatRepository.findByRoomId(roomId).subscribe(i -> {
            sinks.get(roomId).tryEmitNext(ServerSentEvent.builder(ChatDTO.builder()
                    .id(i.getId())
                    .msg(i.getMsg())
                    .sender(i.getSender())
                    .createdAt(i.getCreatedAt())
                    .build()).build());
        });
        return sinks.get(roomId).asFlux().doOnCancel(() -> {
            sinks.get(roomId).tryEmitComplete();
            sinks.remove(roomId);
        });
    }

}
