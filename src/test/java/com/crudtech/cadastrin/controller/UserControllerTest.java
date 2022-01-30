package com.crudtech.cadastrin.controller;

import com.crudtech.cadastrin.model.User;
import com.crudtech.cadastrin.repository.UserRepository;
import com.crudtech.cadastrin.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;
    User user;
    List<User> users;
    String userString;
    String wrongUserString;
    String updatedUserString;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void init() {
        user = new User();
        users = new ArrayList<>();
        userString = "{" +
                "\"id\":1," +
                "\"email\":\"vin.felipes@gmail.com\"," +
                "\"name\":\"Vinicius\"," +
                "\"password\":\"12345678\"," +
                "\"username\":\"vini_gomes\"," +
                "\"active\":true" +
                "}";

        wrongUserString = "{" +
                "\"id\":1," +
                "\"email\":\"vin.felipes@gmail.com\"," +
                "\"name\":\"Vinicius\"," +
                "\"password\":\"aaa\"," +
                "\"username\":\"vini_gomes\"," +
                "\"active\":true" +
                "}";

        updatedUserString = "{" +
                "\"id\":1," +
                "\"email\":\"vin.felipe@gmail.com\"," +
                "\"name\":\"Vinicius\"," +
                "\"password\":\"123456\"," +
                "\"username\":\"vini_gomes\"," +
                "\"active\":true" +
                "}";

        user.setActive(true);
        user.setUsername("vini_gomes");
        user.setName("Vinicius");
        user.setPassword("12345678");
        user.setEmail("vin.felipes@gmail.com");
        user.setId(1L);

        users.add(user);
    }

    @Test
    void all() throws Exception {
        given(this.userService.findAll()).willReturn(users);
        this.mvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(String.valueOf(users)));
    }

    @Test
    void allFails() throws Exception {
        users.clear();
        given(this.userService.findAll()).willReturn(users);
        this.mvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(content().string("Could not find user"));
    }

    @Test
    void get() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.ofNullable(user));
        this.mvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(String.valueOf(user)));
    }

    @Test
    void getFails() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.empty());
        this.mvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(content().string("Could not find user: 1"));
    }

    @Test
    void post() throws Exception {
        given(this.userService.create(user)).willReturn(user);
        this.mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userString)).andExpect(status().isOk()).andExpect(content().json(String.valueOf(user)));
    }

    @Test
    void postFails() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(wrongUserString)).andExpect(status().is4xxClientError()).andExpect(content().string("Validation error: password :size must be between 6 and 2147483647"));
    }

    @Test
    void put() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.ofNullable(user));
        given(this.userService.save(user)).willReturn(user);
        this.mvc.perform(MockMvcRequestBuilders.put("/users/1").contentType(MediaType.APPLICATION_JSON).content(updatedUserString)).andExpect(status().isOk()).andExpect(content().json(String.valueOf(user)));
    }

    @Test
    void putFails() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.ofNullable(user));
        given(this.userService.save(user)).willReturn(user);
        this.mvc.perform(MockMvcRequestBuilders.put("/users/3").contentType(MediaType.APPLICATION_JSON).content(updatedUserString)).andExpect(status().is4xxClientError()).andExpect(content().string("Could not find user: 3"));
    }

    @Test
    void delete() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.ofNullable(user));
        this.mvc.perform(MockMvcRequestBuilders.delete("/users/1")).andExpect(status().isOk());
    }

    @Test
    void deleteFails() throws Exception {
        given(this.userService.findById(1L)).willReturn(Optional.empty());
        this.mvc.perform(MockMvcRequestBuilders.delete("/users/1")).andExpect(status().is4xxClientError()).andExpect(content().string("Could not find user: 1"));
    }
}
