/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.controller;

import org.naic.exception.UserProfileNotFoundException;
import org.naic.model.UserLogin;
import org.naic.model.UserProfile;
import org.naic.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @RequestMapping("/login")
    public boolean login(@RequestBody UserLogin user) {
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

    @CrossOrigin("https://localhost:4200")
    @PutMapping("/userProfile")
    public UserProfile update(@RequestBody UserProfile updatedUserProfile) {
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
