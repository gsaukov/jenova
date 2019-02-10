package com.pro.jenova.omnidrive.rest.controller.notification;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.omnidrive.messaging.notification.NotificationProducer;
import com.pro.jenova.omnidrive.rest.controller.notification.request.RestSendNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.lineSeparator;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@RequestMapping("/omnidrive-api/notification")
public class NotificationController {

    @Autowired
    private NotificationProducer notificationProducer;

    @PreAuthorize("hasAuthority('NOTIFICATION')")
    @PostMapping("/send")
    public ResponseEntity<RestResponse> send(@RequestBody RestSendNotificationRequest restSendNotificationRequest) {
        StringBuilder builder = new StringBuilder();

        builder.append("From: ").append(getSender()).append(lineSeparator());
        builder.append("Message: ").append(restSendNotificationRequest.getContent()).append(lineSeparator());

        notificationProducer.send(builder.toString());

        return VoidResponse.created();
    }

    private String getSender() {
        Authentication authentication = getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return authentication.getName();
        }

        return "anonymous";
    }

}
