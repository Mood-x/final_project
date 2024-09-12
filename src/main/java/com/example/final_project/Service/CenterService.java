package com.example.final_project.Service;

import com.example.final_project.API.ApiException;
import com.example.final_project.DTO.CenterDTO;
import com.example.final_project.Model.Center;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;
    private final AuthRepository authRepository;

    public List<User> getAllCenters(){
        return authRepository.findUserByRole("CENTER");
    }

    public void centerRegister(CenterDTO centerDTO){

        User user = new User();
        user.setUsername(centerDTO.getUsername());
        user.setPassword(centerDTO.getPassword());
        user.setEmail(centerDTO.getEmail());
        user.setName(centerDTO.getName());
        user.setRole("CENTER");

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);

        Center center = new Center();
        center.setId(null);
        center.setPhoneNumber(centerDTO.getPhoneNumber());
        center.setAddress( centerDTO.getAddress());
        center.setActivityType(centerDTO.getActivityType());
        center.setDocuments(centerDTO.getDocuments());

        user.setCenter(center);
        center.setUser(user);

        authRepository.save(user);
        centerRepository.save(center);
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

}
