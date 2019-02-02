package com.pro.jenova.omnidrive.rest.controller.notification;

import com.pro.jenova.common.rest.RestResponse;
import com.pro.jenova.common.rest.VoidResponse;
import com.pro.jenova.omnidrive.data.entity.Notification;
import com.pro.jenova.omnidrive.data.repository.NotificationRepository;
import com.pro.jenova.omnidrive.rest.controller.notification.request.RestSendNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omnidrive-api/notification")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> send(@RequestBody RestSendNotificationRequest restSendNotificationRequest) {
        notificationRepository.save(new Notification.Builder()
                .withRoute(restSendNotificationRequest.getRoute())
                .withContent(restSendNotificationRequest.getContent())
                .build());

        return VoidResponse.created();
    }

}
