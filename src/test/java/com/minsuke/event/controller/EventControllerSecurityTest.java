package com.minsuke.event.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
class EventControllerSecurityTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("minsuke")
            .withUsername("minsuke")
            .withPassword("minsuke");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void calendarRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/calendar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(roles = "PARENT")
    void parentCanViewCalendar() throws Exception {
        mockMvc.perform(get("/calendar"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PARENT")
    void parentCannotCreateEvent() throws Exception {
        mockMvc.perform(post("/events")
                        .with(csrf())
                        .param("title", "テスト")
                        .param("description", "説明")
                        .param("eventDate", "2026-08-15"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanViewEventCreateForm() throws Exception {
        mockMvc.perform(get("/events/new"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PARENT")
    void parentCannotViewEventCreateForm() throws Exception {
        mockMvc.perform(get("/events/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCannotAttendEvent() throws Exception {
        mockMvc.perform(post("/events/1/attend")
                        .with(csrf())
                        .param("action", "register")
                        .param("participantType", "PARENT")
                        .param("parentId", "1"))
                .andExpect(status().isForbidden());
    }
}
