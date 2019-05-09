package com.pro.jenova.omnidrive.rest.controller.notification;

import com.pro.jenova.common.rest.ErrorResponse;
import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.omnidrive.messaging.notification.NotificationProducer;
import com.pro.jenova.omnidrive.rest.controller.notification.request.RestSendNotificationRequest;
import com.pro.jenova.omnidrive.security.CustomClaimsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/omnidrive-api/notification")
public class NotificationController {

    @Autowired
    private CustomClaimsService customClaimsService;

    @Autowired
    private NotificationProducer notificationProducer;

    @PostMapping("/send")
    public ResponseEntity<RestResponse> send(@RequestBody RestSendNotificationRequest restSendNotificationRequest) {
        Optional<Object> secret = customClaimsService.getClaim("secret");

        if (!secret.isPresent()) {
            return ErrorResponse.badRequest("NO_SECRET_PROVIDED_IN_AUTHENTICATION");
        }

        String content = restSendNotificationRequest.getContent();

        if (!content.contains(secret.get().toString())) {
            return ErrorResponse.badRequest("SECRET_NOT_FOUND_IN_NOTIFICATION");
        }

        notificationProducer.sendNotification(content);
        return VoidResponse.created();
    }

}
