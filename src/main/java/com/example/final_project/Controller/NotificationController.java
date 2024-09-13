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
    private final NotificationService notificationService;


    @GetMapping("/get-all-notifications")
    public ResponseEntity<List<Notification>> getAllNotifications(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(notificationService.getAllNotifications(user.getId()));
    }

    @GetMapping("/get-notification")
    public ResponseEntity<Notification> getNotification(@AuthenticationPrincipal User user, Integer notification_id) {
        return ResponseEntity.ok(notificationService.getNotificationById(user.getId(), notification_id));
    }

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

    @PutMapping("/read-notification")
    public ResponseEntity<ApiResponse> readNotification(@AuthenticationPrincipal User user, @RequestParam Integer id){
        notificationService.readNotification(user.getId(), id);
        return ResponseEntity.ok(new ApiResponse("Notification read successfully"));
    }

    // + Mood-EP
    @PutMapping("/read-all-notifications")
    public ResponseEntity<ApiResponse> readAllNotifications(@AuthenticationPrincipal User user){
        notificationService.readAllNotifications(user.getId());
        return ResponseEntity.ok(new ApiResponse("Notifications read successfully"));
    }

    @DeleteMapping("/delete-notification")
    public ResponseEntity<ApiResponse> deleteNotification(@AuthenticationPrincipal User user, @RequestParam Integer id){
        notificationService.deleteNotification(user.getId(), id);
        return ResponseEntity.ok(new ApiResponse("Notification deleted successfully"));
    }
}
