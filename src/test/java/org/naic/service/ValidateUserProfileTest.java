/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.naic.config.WebConfig;
import org.naic.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
@ActiveProfiles("test")
@SpringBootTest
public class ValidateUserProfileTest {

    @Autowired
    private IValidateUserProfileService service;

    @Test
    public void testHappyPath() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Jim");
        userProfile.setLastName("Carrey");
        userProfile.setEmail("jim.carrey@inlivingcolor.com");

        List<String> errorMessages = service.validateUserProfile(userProfile);

        Assert.assertTrue(errorMessages.isEmpty());
    }

    @Test
    public void testNoFirstname() {
        UserProfile userProfile = new UserProfile();
        userProfile.setLastName("Carrey");
        userProfile.setEmail("jim.carrey@inlivingcolor.com");

        List<String> errorMessages = service.validateUserProfile(userProfile);

        Assert.assertFalse(errorMessages.isEmpty());
        Assert.assertEquals("First Name is Required", errorMessages.get(0));
    }

    @Test
    public void testNoLastname() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Jim");
        userProfile.setEmail("jim.carrey@inlivingcolor.com");

        List<String> errorMessages = service.validateUserProfile(userProfile);

        Assert.assertFalse(errorMessages.isEmpty());
        Assert.assertEquals("Last Name is Required", errorMessages.get(0));
    }

    @Test
    public void testNoEmail() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Jim");
        userProfile.setLastName("Carrey");

        List<String> errorMessages = service.validateUserProfile(userProfile);

        Assert.assertFalse(errorMessages.isEmpty());
        Assert.assertEquals("Email is Required", errorMessages.get(0));
    }

    @Test
    public void testNonUniqueEmail() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("Jim");
        userProfile.setLastName("Carrey");

        //bwayne@wayneenterprises.com already exists, see Application.java
        userProfile.setEmail("bwayne@wayneenterprises.com");

        List<String> errorMessages = service.validateUserProfile(userProfile);

        Assert.assertFalse(errorMessages.isEmpty());
        Assert.assertEquals("Email is not Unique", errorMessages.get(0));
    }
}
