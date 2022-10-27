package com.websocketo02.websocket02.controllers;


import com.websocketo02.websocket02.dto.ClientMessageDTO;
import com.websocketo02.websocket02.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    SimpMessagingTemplate smt;

    @PostMapping("/tobroadcast")
    public ResponseEntity sendMessageToBroadcast(@RequestBody MessageDTO message) {
        smt.convertAndSend("/topic/broadcast", message);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @SendTo("/topic/broadcast")
    @MessageMapping("/client-message")
    public ClientMessageDTO sendMessageFromClient(ClientMessageDTO message) {
        return new ClientMessageDTO(message.getClientName(), message.getClientAlert(), message.getClientMSG());
    }
}