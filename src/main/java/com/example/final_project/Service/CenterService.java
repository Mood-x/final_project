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
    public List<Center> getAllCenters(){
        return centerRepository.findAll();
    }

    public void centerRegister(CenterDTO centerDTO){
//        User user = new User(
//                null,
//                centerDTO.getUsername(),
//                centerDTO.getEmail(),
//                centerDTO.getPassword(),
//                centerDTO.getName(),"Center",
//                null,
//                null,
//                null,
//                null,
//                null,
//        );

        User user = new User();
        user.setId(null);
        user.setUsername(centerDTO.getUsername());
        user.setPassword(centerDTO.getPassword());
        user.setEmail(centerDTO.getEmail());
        user.setName(centerDTO.getName());
        user.setRole("CENTER");

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);



        Center center = new Center(user.getId(),centerDTO.getPhoneNumber(),centerDTO.getAddress(),centerDTO.getDocuments(),
                centerDTO.getActivityType(),user);

        user.setCenter(center);
        centerRepository.save(center);
        authRepository.save(user);
    }

    public void updateCenter(Integer authId, CenterDTO centerDTO){
        User user = authRepository.findUserById(authId)
                .orElseThrow(()-> new ApiException("User Not Found"));

        user.setUsername(centerDTO.getUsername());
        user.setEmail(centerDTO.getEmail());
        user.setPassword(centerDTO.getPassword());
        user.setName(centerDTO.getName());
        user.getCenter().setAddress( centerDTO.getAddress());
        user.getCenter().setDocuments( centerDTO.getDocuments());
        user.getCenter().setActivityType(centerDTO.getActivityType());
        user.getCenter().setPhoneNumber(centerDTO.getPhoneNumber());

        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);


        authRepository.save(user);
    }

    public void deleteCenter(Integer centerID,Integer authId){
        User user = authRepository.findUserById(centerID)
                .orElseThrow(()-> new ApiException("User Not Found"));

        if(!user.getId().equals(authId)){
            throw new ApiException("Sorry you don't have permission to update this center.");
        }
    }



}
