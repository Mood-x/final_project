//package com.example.final_project.Service;
//
//import com.example.final_project.API.ApiException;
//import com.example.final_project.Model.*;
//import com.example.final_project.Repository.AuthRepository;
//import com.example.final_project.Repository.CertificateRepository;
//import com.example.final_project.Repository.ChildRepository;
//import com.example.final_project.Repository.ParentReposotiry;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CertificateService {
//    private final CertificateRepository certificateRepository;
//    private final AuthRepository authRepository;
//    private final ParentReposotiry parentReposotiry;
//    private final ChildRepository childRepository;
//
//    public List<Certificate> getAllCertificates() {
//        return certificateRepository.findAll();
//    }
//
//    public List<Certificate> childCertificates(int userId, int childId) {
//
//        User user = authRepository.findUserById(userId)
//                .orElseThrow(() -> new ApiException("User not found"));
//
//        Parent parent = parentReposotiry.findParentById(user.getId())
//                .orElseThrow(() -> new ApiException("parent not found"));
//
//        Child child = childRepository.findChildById(childId)
//                .orElseThrow(() -> new ApiException("child not found"));
//
//        if (child.getParent() != parent) {
//            throw new ApiException("Parent and child are not the same");
//        }
//
//        return certificateRepository.findCertificateByChild(child);
//    }
//
//    public void createChildCertificate(Child child, Program program){
//
//
//        LocalDateTime now = LocalDateTime.now();
//
//        List<Certificate> certificates = certificateRepository.findCertificateRequestDateBefore(now);
//
//        for (Certificate certificate : certificates) {
//            if () {
//                Certificate certificate = new Certificate();
//                certificate.setCertificateDescription("Congrats for your child "+ child.getName() +" graduation from the '" +program.getTitle()+"' \n we hoop a Good future for him/her.\n");
//                certificate.setChild(child);
//                certificate.setProgram_Competition(program.getTitle());
//                certificate.setCenterName(program.getCenter().getUser().getName());
//                certificate.setGraduation_date(programEndDate);
//                child.getCertificates().add(certificate);
//                certificateRepository.save(certificate);
//                childRepository.save(child);
//            }
//        }
//    }
//
//
//}
