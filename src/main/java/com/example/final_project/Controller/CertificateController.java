package com.example.final_project.Controller;

import com.example.final_project.Model.Certificate;
import com.example.final_project.Model.User;
import com.example.final_project.Service.CertificateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificate")
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("get-all-certificates")
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.status(200).body(certificateService.getAllCertificates());
    }

    @GetMapping("get-my-certificate/{childid}")
    public ResponseEntity getMyCertificate(@AuthenticationPrincipal User user,@PathVariable int childid) {
        return ResponseEntity.status(200).body(certificateService.childCertificates(user.getId(),childid));
    }

    // Endpoint to issue a certificate to a child after a program ends
    @PostMapping("/issue/{childId}/{programId}")
    public ResponseEntity<Certificate> issueCertificate(
            @PathVariable Integer childId,
            @PathVariable Integer programId,
            @RequestBody Certificate certificateDetails) {
        Certificate issuedCertificate = certificateService.issueCertificate(childId, programId, certificateDetails);
        return ResponseEntity.status(201).body(issuedCertificate);
    }
}
