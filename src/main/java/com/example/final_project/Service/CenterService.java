package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import com.example.final_project.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;
    private final AuthRepository authRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public List<User> getAllCenters(){
        return authRepository.findUserByRole("CENTER");
    }

    public void centerRegister(CenterDTO centerDTO){

        User user = new User();
        user.setUsername(centerDTO.getUsername());
        user.setPassword(centerDTO.getPassword());
        user.setEmail(centerDTO.getEmail());
        user.setName(centerDTO.getName());
        user.setRole(centerDTO.getRole());

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        Center center = new Center();
        center.setId(null);
        center.setPhoneNumber(centerDTO.getPhoneNumber());
        center.setAddress( centerDTO.getAddress());
        center.setActivityType(centerDTO.getActivityType());
        center.setDocuments(centerDTO.getDocuments());
        center.setStatus(Center.Status.IN_PROGRESS); // +

        user.setCenter(center);
        center.setUser(user);

        authRepository.save(user);
        centerRepository.save(center);


        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));

        notificationService.createNotification(
                center.getUser(),
                admin,
                "A new center registration request has been submitted. please review the details",
                Notification.NotificationType.CENTER_REQUEST_REGISTRATION
        );

//        Notification notification = new Notification();
//        notification.setSender(center.getUser());
//        notification.setReceiver(admin);
//        notification.setMessage("A new center registration request has been submitted. please review the details");
//        notification.setUser(admin);
//        notificationRepository.save(notification);
    }

    public void updateCenter(Integer authId, CenterDTO centerDTO){

        User user = authRepository.findUserById(authId)
                .orElseThrow(()-> new ApiException("User Not Found"));

        user.setUsername(centerDTO.getUsername());
        user.setEmail(centerDTO.getEmail());
        user.setPassword(centerDTO.getPassword());
        user.setName(centerDTO.getName());
        user.getCenter().setPhoneNumber(centerDTO.getPhoneNumber());
        user.getCenter().setAddress(centerDTO.getAddress());
        user.getCenter().setActivityType(centerDTO.getActivityType());
        user.getCenter().setDocuments(centerDTO.getDocuments());

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        authRepository.save(user);

    }



    public void deleteCenter(Integer centerID,Integer authId){
            User user = authRepository.findUserById(centerID)
                    .orElseThrow(()-> new ApiException("User Not Found"));

            if(!user.getRole().equals("ADMIN")){
                throw new ApiException("Sorry you don't have permission to update this center.");
            }
            authRepository.deleteById(authId);
    }


    public String showMyCenterAccount(Integer userCenterId){
        Center center = centerRepository.findCenterById(userCenterId);
        return " UserName:" + center.getUser().getUsername()+
                "\n Name:" + center.getUser().getName()+
                "\n Email: " + center.getUser().getEmail()+
                "\n Address: " + center.getAddress() +
                "\n PhoneNumber: " + center.getPhoneNumber();
    }

    public void changePassword(Integer userCenterId, String oldPassword, String newPassword){
        Center center = centerRepository.findCenterById(userCenterId);

        //check old password with entered old password
        boolean isMatched = new BCryptPasswordEncoder().matches(oldPassword,center.getUser().getPassword());
        if(!isMatched){
            throw new ApiException("Sorry password doesn't match.");
        }


        String hashNew=new BCryptPasswordEncoder().encode(newPassword);
        center.getUser().setPassword(hashNew);
        authRepository.save(center.getUser());
    }

    public void approveCenterRegistration(Integer centerId){
        Center center = centerRepository.findCenterById(centerId);
        if(center == null){
            throw new ApiException("Center not found");
        }

        center.setStatus(Center.Status.APPROVED);
        centerRepository.save(center);

        User admin = authRepository.findUserById(1)
                .orElseThrow(() -> new ApiException("Admin not found"));
        notificationService.createNotification(
                admin,
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
        notificationService.createNotification(
                admin,
                center.getUser(),
                "Your center registration has been rejected. Reason: " + rejectionReason,
                Notification.NotificationType.ADMIN_TO_CENTER
        );
    }

    //mood
//    public void requestToDeleteAccount(Integer centerId){
//        User center = authRepository.findUserById(centerId)
//                .orElseThrow(()-> new ApiException("User Not Found"));
//        if(center.isPendingDeletion()){
//            throw new ApiException("Sorry this account has been pending deletion.");
//        }
//
//        center.setPendingDeletion(true);
//        center.setDeletionRequestDate(LocalDateTime.now());
//        authRepository.save(center);
//    }
}
