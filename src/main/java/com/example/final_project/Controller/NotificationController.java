package com.example.final_project.Controller;

import com.example.final_project.API.ApiResponse;
import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import com.example.final_project.Service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    // [ Mohammed ]
    private final NotificationService notificationService;


    @GetMapping("/get-all-my-notifications")
    public ResponseEntity<List<Notification>> getAllMyNotifications(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(notificationService.getAllMyNotifications(user.getId()));
    }

    @GetMapping("/get-notification-by-id/{notification_id}")
    public ResponseEntity<Notification> getNotificationById(@AuthenticationPrincipal User user, @PathVariable  Integer notification_id) {
        return ResponseEntity.ok(notificationService.getNotificationById(user.getId(), notification_id));
    }

    // [End-Point]
    @PostMapping("/add-notification")
    public ResponseEntity<ApiResponse> addNotification(@AuthenticationPrincipal User user, @Valid @RequestBody Notification notification){
        notificationService.addNotification(user.getId(), notification);
        return ResponseEntity.ok(new ApiResponse("Notification added successfully"));
    }

    @PutMapping("/update-notification")
    public ResponseEntity<ApiResponse> updateNotification(@AuthenticationPrincipal User user, @RequestParam Integer id,  @Valid @RequestBody Notification notification){
        notificationService.updateNotification(user.getId(), id, notification);
        return ResponseEntity.ok(new ApiResponse("Notification updated successfully"));
    }

    // +[End-Point]
    @DeleteMapping("/delete-notification")
    public ResponseEntity<ApiResponse> deleteNotification(@AuthenticationPrincipal User user, @RequestParam Integer id){
        notificationService.deleteNotification(user.getId(), id);
        return ResponseEntity.ok(new ApiResponse("Notification deleted successfully"));
    }

    // +[End-Point]
    @DeleteMapping("/delete-all-my-notifications")
    public ResponseEntity<ApiResponse> deleteAllMyNotifications(@AuthenticationPrincipal User user){
        notificationService.deleteAllMyNotifications(user.getId());
        return ResponseEntity.ok(new ApiResponse("All notifications deleted successfully"));
    }


    @PutMapping("/read-notification/{notificationId}")
    public ResponseEntity<ApiResponse> readNotification(@AuthenticationPrincipal User user, @PathVariable Integer notificationId){
        notificationService.readNotification(user.getId(), notificationId);
        return ResponseEntity.ok(new ApiResponse("Notification read successfully"));
    }

    // +[End-Point]
    @PutMapping("/read-all-notifications")
    public ResponseEntity<ApiResponse> markAllAsRead(@AuthenticationPrincipal User user){
        notificationService.markAllAsRead(user.getId());
        return ResponseEntity.ok(new ApiResponse("Notifications read successfully"));
    }

    // +[End-Point]
    @GetMapping("/get-all-notifications-is-not-reading")
    public ResponseEntity<List<Notification>> getAllNotificationsIsNotReading(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(notificationService.getAllNotificationsIsNotReading(user.getId()));
    }
}
