package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.Model.Advertisement;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.Notification;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AdvertisementRepository;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final AuthRepository authRepository;
    private final NotificationService notificationService;
    private final CenterRepository centerRepository;

    public List<Advertisement> getAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    public void addAdvertisement(int userId, Advertisement advertisement) {

        //add the advert
        User user = authRepository.findUserById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

//        ArrayList<Advertisement> check = new ArrayList<>();


//        for (int i = 0; i < user.getCenter().getAdvertisements().size(); i++) {
//            user.getCenter().getAdvertisements().contains()
//        }

        if (!user.getCenter().getAdvertisements().isEmpty()){
            throw new RuntimeException("You have already added an advertisement");
        }

        advertisement.setPrice(advertisement.getDaysDuration()*10);
        advertisement.setCenter(user.getCenter());
        user.getCenter().getAdvertisements().add(advertisement);
        advertisementRepository.save(advertisement);
        authRepository.save(user);

        // add advert notification request to admin
        User admin = authRepository.findUserById(1).orElseThrow(() -> new ApiException("Admin not found"));

        notificationService.createNotification(
                user,
                admin,
                "A new advertisement add request has been submitted. please review the details",
                Notification.NotificationType.CENTER_TO_ADMIN
        );
    }

    public void approveCenterAdvert(Integer centerId, Integer advertisementId){
        Center center = centerRepository.findCenterById(centerId)

                .orElseThrow(() -> new ApiException("Center Not Found"));


        Advertisement advert = advertisementRepository.findAdvertisementById(advertisementId)
                .orElseThrow(() -> new ApiException("advertisement Not Found"));

        if (center!=advert.getCenter()){
            throw new RuntimeException("Center and Advertisement do not match");
        }


        advert.setStatus(Center.Status.APPROVED);
        advertisementRepository.save(advert);

        User admin = authRepository.findUserById(1)
                .orElseThrow(() -> new ApiException("Admin not found"));
        notificationService.createNotification(
                admin,
                center.getUser(),
                "Your advertisement has been approved to be published.",
                Notification.NotificationType.ADMIN_TO_CENTER
        );
    }

    public List<Advertisement> getMyAdverts(Integer userId) {

        User user = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("User Not Found"));

        Center center = centerRepository.findCenterById(user.getId())
                .orElseThrow(() -> new ApiException("Center Not Found"));

        return advertisementRepository.findAdvertisementByCenter(center);
    }


    public void rejectCenterAdvert(Integer centerId,Integer advertId ,String rejectionReason){
        Center center = centerRepository.findCenterById(centerId)

                .orElseThrow(() -> new ApiException("Center Not Found"));


        Advertisement advert = advertisementRepository.findAdvertisementById(advertId)
                .orElseThrow(() -> new ApiException("advertisement Not Found"));

        if (center!=advert.getCenter()){
            throw new RuntimeException("Center and Advertisement do not match");
        }


        advert.setStatus(Center.Status.REJECTED);
        advertisementRepository.save(advert);

        User admin = authRepository.findUserById(1)
                .orElseThrow(() -> new ApiException("Admin not found"));
        notificationService.createNotification(
                admin,
                center.getUser(),
                "Your center advertisement with id: "+advertId+", has been rejected. Reason: " + rejectionReason,
                Notification.NotificationType.ADMIN_TO_CENTER
        );
    }


    // remove all rejected adverts
    public void deleteAllRejectedAdverts(int userId) {
        User admin = authRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException("U ARE NOT THE ADMIN"));

        if (!admin.getRole().equalsIgnoreCase("ADMIN")){
            throw new RuntimeException("You do not have permission to delete rejected adverts");
        }

        List<Advertisement> advertisements = advertisementRepository.findAll();
        for (Advertisement advertisement : advertisements) {
            if (advertisement.getStatus().equals(Center.Status.REJECTED)){
                advertisementRepository.delete(advertisement);
            }
        }
    }
}

