/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.controller;

import org.naic.exception.UserProfileNotFoundException;
import org.naic.model.UserLogin;
import org.naic.model.UserProfile;
import org.naic.persistence.UserProfileRepository;
import org.naic.service.IValidateUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@Validated
public class UserController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private IValidateUserProfileService validateUserProfileService;

    @RequestMapping("/login")
    public boolean login(@RequestBody UserLogin user) {
        // Assume Clark Kent already is a registered User
        return "ckent".equals(user.getUsername()) && "superman".equals(user.getPassword());
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

    @GetMapping("/userProfile")
    public UserProfile getUser(Authentication principal) { ;
        return userProfileRepository.findUserProfileByUsername();
    }

    @PutMapping("/userProfile")
    public UserProfile update(@RequestBody UserProfile updatedUserProfile) {
        List<String> errorMessages = validateUserProfileService.validateUserProfile(updatedUserProfile);
        if (!errorMessages.isEmpty()) {
            throw new RuntimeException(errorMessages.toString());
        }
        return Optional.of(userProfileRepository.findUserProfileByUsername())
                .map(x -> {
                    x.setFirstName(updatedUserProfile.getFirstName());
                    x.setLastName(updatedUserProfile.getLastName());
                    x.setMiddleInit(updatedUserProfile.getMiddleInit());
                    x.setEmail(updatedUserProfile.getEmail());
                    x.setAddress1(updatedUserProfile.getAddress1());
                    x.setAddress2(updatedUserProfile.getAddress2());
                    x.setCity(updatedUserProfile.getCity());
                    x.setState(updatedUserProfile.getState());
                    x.setZip(updatedUserProfile.getZip());
                    x.setPhone(updatedUserProfile.getPhone());
                    return userProfileRepository.save(x);
                }).orElseThrow(() -> {
                    throw new UserProfileNotFoundException();
                });
    }
}
