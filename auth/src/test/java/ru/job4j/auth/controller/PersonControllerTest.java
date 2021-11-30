package ru.job4j.auth.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonController pc;

    @Test
    @WithMockUser
    public void whenGetPersonsThenStatus200AndLoginRoot() throws Exception {
        pc.create(Person.of(0, "root", "123"));
        this.mockMvc.perform(get("/person/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].login", is("root")));
    }

    @Test
    @WithMockUser
    public void whenGetPersonNonExistentIdThenStatus404() throws Exception {
        for (Person person : pc.findAll()) {
            System.out.println(person);
        }
        System.out.println();
        this.mockMvc.perform(get("/person/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void whenGetPersonThenStatus200AndLoginRoot() throws Exception {
        pc.create(Person.of(0, "root", "123"));
        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login", is("root")));
    }

    @Test
    @WithMockUser
    public void whenCreatePersonThenStatus201() throws Exception {
        this.mockMvc.perform(post("/person/")
                .content("{\"login\":\"root\", \"password\":\"123\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login", is("root")));
    }

    @Test
    @WithMockUser
    public void whenUpdatePersonThenStatus200AndLoginR() throws Exception {
        pc.create(Person.of(0, "root", "123"));
        this.mockMvc.perform(put("/person/")
                .content("{\"id\":\"1\", \"login\":\"r\",\"password\":\"1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login", is("r")));
    }

    @Test
    @WithMockUser
    public void whenDeletePersonThenStatus200AndWhenFindByIdThenStatus404() throws Exception {
        pc.create(Person.of(0, "root", "123"));
        this.mockMvc.perform(delete("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}