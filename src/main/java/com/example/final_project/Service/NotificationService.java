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
        notificationRepository.save(notification);
    }


    public void participationRequest(Integer notificationId, boolean isApproved){
        Notification notification = notificationRepository.findNotificationById(notificationId)
                .orElseThrow(() -> new ApiException("Notification not found"));

        if(notification.getNotificationType() != Notification.NotificationType.REQUEST_PARTICIPATION){
            throw new ApiException("Invalid notification type");
        }

        User parent = notification.getSender();
        User admin = notification.getReceiver();

        if(isApproved){
            createNotification(admin, parent, "Your request for child participation has been approved", Notification.NotificationType.REQUEST_PARTICIPATION);
            notification.setMessage("Request approved");

        }else {
            createNotification(admin, parent, "Your request for child participation has been rejected", Notification.NotificationType.REQUEST_PARTICIPATION);
            notification.setMessage("Request rejected");
        }
        notificationRepository.save(notification);
    }


//    public void requestParticipationInCompetition(Integer parentId, Integer childId, Integer competitionId){
//        User parent = authRepository.findUserById(parentId)
//                .orElseThrow(() -> new ApiException("Parent not found"));
//        Child child = childRepository.findChildById(childId)
//                .orElseThrow(() -> new ApiException("Child not found"));
//        Competition competition  = competitionRepository.findCompetitionById(competitionId)
//                .orElseThrow(() -> new ApiException("Competition not found"));
//
//        if(!child.getParent().getId().equals(parentId)){
//            throw new ApiException("You are not authorized to make this request for this child");
//        }
//
//        User admin = authRepository.findUserById(1)
//                .orElseThrow(() -> new ApiException("User Not Found"));
//
//        Notification notification = new Notification();
//        notification.setSender(parent);
//        notification.setReceiver(admin);
//        notification.setMessage("Parent " + parent.getName() + " requested participation for child " + child.getName() + " in competition " + competition.getName());
//        notification.setNotificationType(Notification.NotificationType.REQUEST_PARTICIPATION);
//        notification.setUser(admin);
//        notificationRepository.save(notification);
//    }

    public void approveCenterRegistration(Integer centerId){
        Center center = centerRepository.findCenterById(centerId);
        if(center == null){
            throw new ApiException("Center not found");
        }

        center.setStatus(Center.Status.APPROVED);
        centerRepository.save(center);

        User admin = authRepository.findUserById(8)
                        .orElseThrow(() -> new ApiException("Admin not found"));
        createNotification(
                admin, // admin
                center.getUser(),
                "Your center registration has been approved. You can now access your features",
                Notification.NotificationType.ADMIN_TO_CENTER
        );
    }

    public void rejectCenterRegistration(Integer centerId, String rejectionReason){
        Center center = centerRepository.findCenterById(centerId);

        if(center == null){
            throw new ApiException("Center not found");
        }

        User admin = authRepository.findUserById(1)
                        .orElseThrow(() -> new ApiException("Admin not found"));


        center.setStatus(Center.Status.REJECTED);
        centerRepository.save(center);
        createNotification(
                admin,
                center.getUser(),
                "Your center registration has been rejected. Reason: " + rejectionReason,
                Notification.NotificationType.ADMIN_TO_CENTER
        );
    }
}