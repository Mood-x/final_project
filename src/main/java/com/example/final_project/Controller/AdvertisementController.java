package com.example.final_project.Controller;


import com.example.final_project.Model.Advertisement;
import com.example.final_project.Model.User;
import com.example.final_project.Service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;


    @PostMapping("/add-advert")
    public ResponseEntity addAdvert(@AuthenticationPrincipal User user, @RequestBody Advertisement advertisement){
        advertisementService.addAdvertisement(user.getId(),advertisement);
        return ResponseEntity.status(200).body("Advertisement added successfully");
    }

    @GetMapping("/display-all-adverts")
    public ResponseEntity displayAllAdverts(){
        return ResponseEntity.status(200).body(advertisementService.getAllAdvertisements());
    }

    @PutMapping("/approve-advert/centerid/{cid}/advertid/{aid}")
    public ResponseEntity approveAdvert(@AuthenticationPrincipal User user,@PathVariable Integer cid, @PathVariable Integer aid) {
        advertisementService.approveCenterAdvert(cid,aid);
        return ResponseEntity.status(200).body("Advertisement approved successfully");
    }

    @GetMapping("/get-my-adverts")
    public ResponseEntity getMyAdverts(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(advertisementService.getMyAdverts(user.getId()));
    }

    @PutMapping("/reject-center-advert/-center-id/{cid}/-advert-id/{aid}/{rejectreason}")
    public ResponseEntity rejectAdvert(@AuthenticationPrincipal User user,@PathVariable Integer cid,@PathVariable int aid,@PathVariable String rejectreason){
        advertisementService.rejectCenterAdvert(cid,aid,rejectreason);
        return ResponseEntity.status(200).body("Advertisement rejected successfully");
    }

    @DeleteMapping("/remove-rejected-adverts")
    public ResponseEntity removeRejectedAdverts(@AuthenticationPrincipal User user){
        advertisementService.deleteAllRejectedAdverts(user.getId());
        return ResponseEntity.status(200).body("Rejected Advertisements removed successfully");
    }
}
