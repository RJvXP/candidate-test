/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.controller;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.naic.model.UserProfile;
import org.naic.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileRepository mockUserProfileRepository;

    private UserProfile superman = new UserProfile("ckent","Clark", "J", "Kent", "ckent@dailyplanet.com", "1938 Sullivan Lane", null, "Metropolis", "New York", "33866", "5309384645");


    @WithMockUser("USER")
    @Test
    public void testHappyPath() throws Exception {
        Mockito.when(mockUserProfileRepository.findUserProfileByUsername())
                .thenReturn(superman);

      mockMvc.perform((MockMvcRequestBuilders.get("/userProfile")))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Clark")))
              .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Kent")));
    }

    @Test
    public void testUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/userProfile"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
