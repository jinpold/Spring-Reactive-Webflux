package store.ggun.admin.chat.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import store.ggun.admin.chat.domain.ChatDTO;
import store.ggun.admin.chat.domain.ChatModel;
import store.ggun.admin.chat.service.ChatService;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping(path = "/receive/{id}")
    public Flux<ServerSentEvent<ChatDTO>> receiveByRoomId(@PathVariable String id) {
        log.info("Receive request received : {}", id);
        return chatService.connect(id).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/send")
    public Mono<Boolean> send(@RequestBody ChatModel entity) {
        return chatService.save(entity).subscribeOn(Schedulers.boundedElastic());
    }

}