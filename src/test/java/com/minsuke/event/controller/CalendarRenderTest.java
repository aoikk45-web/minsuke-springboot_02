package com.minsuke.event.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
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
class CalendarRenderTest {

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
    @WithMockUser(roles = "ADMIN")
    void calendarPageRenders() throws Exception {
        mockMvc.perform(get("/calendar"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("nav-toggle")))
                .andExpect(content().string(Matchers.containsString("calendar-scroll")))
                .andExpect(content().string(Matchers.not(Matchers.containsString("参加履歴"))));
    }

    @Test
    @WithMockUser(roles = "PARENT")
    void parentCalendarShowsParticipationHistoryNav() throws Exception {
        mockMvc.perform(get("/calendar"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("参加履歴")));
    }
}
