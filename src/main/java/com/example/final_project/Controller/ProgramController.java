package com.example.final_project.Controller;

import com.example.final_project.Model.Program;
import com.example.final_project.Model.User;
import com.example.final_project.Service.ProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/program")
public class ProgramController {

    private final ProgramService programService;

    @PostMapping("/add-program")
    public ResponseEntity addProgram(@AuthenticationPrincipal User user,@Valid @RequestBody Program program) {
        programService.addProgram(user.getId(),program);
        return ResponseEntity.status(200).body("program successfully added");
    }


    @GetMapping("get-programs")
    public ResponseEntity getPrograms(){
        return ResponseEntity.status(200).body(programService.getAllPrograms());
    }


    @PutMapping("update-program/{programid}")
    public ResponseEntity updateProgram(@AuthenticationPrincipal User user,@PathVariable int programid,@Valid @RequestBody Program program) {
        programService.updateProgram(user.getId(),programid,program);
        return ResponseEntity.status(200).body("program successfully updated");
    }

    @GetMapping("get-my-programs-by-center")
    public ResponseEntity getMyProgramsByCenter(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(programService.displayAllMyPrograms(user.getId()));
    }

    @GetMapping("/displayChosedCenterPrograms/{centerid}")
    public ResponseEntity displayChosedCenterPrograms(@PathVariable int centerid){
        return ResponseEntity.status(200).body(programService.displayProgramsInCenterPage(centerid));
    }

    @PutMapping("/set-program-stetus/{programid}/{status}")
    public ResponseEntity setProgramStatus(@AuthenticationPrincipal User user,@PathVariable int programid,@PathVariable String status){
        programService.setProgramStatus(user.getId(),programid,status);
        return ResponseEntity.status(200).body("program successfully updated");
    }

    @DeleteMapping("/delete-program/{programid}")
    public ResponseEntity deleteProgram(@AuthenticationPrincipal User user,@PathVariable int programid){
        programService.deleteProgram(user.getId(),programid);
        return ResponseEntity.status(200).body("program successfully deleted");
    }

    @DeleteMapping("/delete-all-closed-programs")
    public ResponseEntity deleteAllClosedPrograms(@AuthenticationPrincipal User user){
        programService.deleteAllClosedPrograms(user.getId());
        return ResponseEntity.status(200).body("program successfully deleted");
    }

    // Endpoint to search programs by title
    @GetMapping("/search")
    public ResponseEntity<List<Program>> searchProgramsByTitle(@RequestParam String title) {
        List<Program> programs = programService.searchProgramsByTitle(title);
        return ResponseEntity.ok(programs);
    }


    @GetMapping("display-programs-financial-returns/{programid}")
    public ResponseEntity displayProgramsFinancialReturns(@AuthenticationPrincipal User user,@PathVariable int programid) {
        return ResponseEntity.status(200).body(programService.displayProgramFinancialReturn(user.getId(), programid));
    }

    @GetMapping("/display-open-programs")
    public ResponseEntity displayOpenPrograms(){
        return ResponseEntity.status(200).body(programService.getOpenPrograms());
    }

    @GetMapping("/get-program-by/min-age/{minage}/max-age/{maxage}")
    public ResponseEntity getProgrambyAge(@PathVariable int minage,@PathVariable int maxage){
        return ResponseEntity.status(200).body(programService.getProgramByAges(minage,maxage));
    }

    @GetMapping("/display-number-of-children-in-the-program/{programid}")
    public ResponseEntity displayNumOfChildrenInTheProgram(@AuthenticationPrincipal User user,@PathVariable int programid){
        return ResponseEntity.status(200).body(programService.displayChildrenNumbers(user.getId(), programid));
    }


}
