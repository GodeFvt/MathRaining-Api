package sit.project.mathrainingapi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.project.mathrainingapi.dtos.PaginationResponseDTO;
import sit.project.mathrainingapi.entities.UserProfile;
import sit.project.mathrainingapi.services.UserProfileService;

import java.util.List;

@RestController
@RequestMapping("/usersProfile")
@CrossOrigin(origins = {"http://localhost:5173/","https://phuttinanphak.com/"})
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("")
    public ResponseEntity<List<UserProfile>> getAllUserProfile() {
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUserProfile(@RequestParam(defaultValue = "rankingHighScore") String _sort,
                                               @RequestParam(defaultValue = "1") Integer _page,
                                               @RequestParam(defaultValue = "5") Integer _per_page,
                                               @RequestParam(defaultValue = "desc") String _sortDirection) {
        return ResponseEntity.ok(userProfileService.getAllUserProfile(_sort, _page, _per_page, _sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable String id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }

    @PostMapping("")
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.createUserProfile(userProfile));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable String id, @RequestBody UserProfile userProfile) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfiles(@PathVariable String id, @RequestBody UserProfile userProfile) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable String id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.ok().build();
    }
}
