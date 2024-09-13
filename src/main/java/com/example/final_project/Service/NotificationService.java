package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.*;
import com.example.final_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final AuthRepository authRepository;
    private final NotificationRepository notificationRepository;
    private final ChildRepository childRepository;
//    private final CompetitionRepository competitionRepository;
    private final CenterRepository centerRepository;

    // + Mood-EP
    public List<Notification> getAllNotifications(Integer authId) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User Not Found"));

        if(!user.getId().equals(authId)){
            throw new ApiException("Sorry you don't have permission to access this resource");
        }
        return notificationRepository.findAllByUser(user).orElseThrow(() -> new ApiException("Not found notifications"));
    }

    public Notification getNotificationById(Integer authId, Integer id) {
        Notification notification = notificationRepository.findNotificationById(id)
                .orElseThrow(() -> new ApiException("Notification not found"));
        if(!notification.getUser().getId().equals(authId)){
            throw new ApiException("Sorry you don't have permission to access this resource");
        }

        return notification;
    }

    public void addNotification(Integer authId, Notification notification) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User Not Found"));

        if(!user.getId().equals(authId)){
            throw new ApiException("Sorry you don't have permission to access this resource");
        }

        notification.setUser(user);
        notificationRepository.save(notification);
    }


//    مانحتاجه
    public void updateNotification(Integer authId, Integer id, Notification updatedNotification) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User Not Found"));

        Notification notification = notificationRepository.findNotificationById(id)
                .orElseThrow(() -> new ApiException("Notification not found"));

        notification.setMessage(updatedNotification.getMessage());
        notification.setUser(user);
        notificationRepository.save(notification);
    }


    public void deleteNotification(Integer authId, Integer id) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));

        Notification notification = notificationRepository.findNotificationById(id)
                .orElseThrow(() -> new ApiException("Notification not found"));

        if(!notification.getUser().getId().equals(user.getId())) {
            throw new ApiException("Sorry you don't have permission to delete this notification");
        }

        notificationRepository.delete(notification);
    }


    // + Mood-EP
    public void readNotification(Integer authId, Integer id) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));

        Notification notification = notificationRepository.findNotificationById(id)
                .orElseThrow(() -> new ApiException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // + Mood-EP
    public void deleteAllNotifications(Integer authId, Integer userId) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));

        user.getNotifications().clear();
    }

//    + Mood-EP
    public void readAllNotifications(Integer authId) {
        User user = authRepository.findUserById(authId)
                .orElseThrow(() -> new ApiException("User not found"));

        List<Notification> notifications = notificationRepository.findAllByUser(user)
                .orElseThrow(() -> new ApiException("Not found notifications"));

        notifications.stream()
                .filter(notification -> !notification.isRead())
                .forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }

    public void createNotification(User sender, User receiver, String message, Notification.NotificationType type){
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setUser(receiver);
        notificationRepository.save(notification);
    }
}