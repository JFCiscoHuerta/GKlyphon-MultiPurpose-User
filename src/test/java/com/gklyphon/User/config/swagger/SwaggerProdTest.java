package com.gklyphon.User.config.swagger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("prod")
@AutoConfigureMockMvc
class SwaggerProdTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testSwaggerForbiddenInNonDevProfile() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/swagger-ui/index.html")
        )
                .andExpect(status().isNotFound());
    }

}
