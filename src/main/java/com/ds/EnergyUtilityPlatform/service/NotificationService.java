package com.ds.EnergyUtilityPlatform.service;

import com.ds.EnergyUtilityPlatform.model.dto.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


/**
 * Service class for sending notification messages.
 */
@Service
@RequiredArgsConstructor
public class
NotificationService {

    // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
    private final SimpMessagingTemplate messagingTemplate;

    public void notify(Notification notification, String username) {
        String destination = "/topic/notify/" + username;
//        messagingTemplate.convertAndSendToUser(
//                username,
//                "/queue/notify",
//                notification
//        );

        messagingTemplate.convertAndSend(destination, notification);
    }


}
