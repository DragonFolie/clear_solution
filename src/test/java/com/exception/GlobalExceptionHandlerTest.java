package com.exception;

import com.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Locale;
import java.util.NoSuchElementException;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestPropertySource(locations = "classpath:error-messages.properties")
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageSource messageSource;

    @Mock
    private PersonController personController;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testGlobalException() throws Exception {
        when(personController.findAll()).thenThrow(new RuntimeException());

        String errorMessage = messageSource.getMessage("internal.server.error.message", null, Locale.getDefault());

        mockMvc.perform(get("/person/"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        when(personController.createPerson(null)).thenThrow(new IllegalArgumentException());

        String errorMessage = messageSource.getMessage("error.message.bad_request", null, Locale.getDefault());

        mockMvc.perform(get("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    public void testHandleNoSuchElementException() throws Exception {
        when(personController.findAll()).thenThrow(new NoSuchElementException());

        String errorMessage = messageSource.getMessage("not.found.error.message", null, Locale.getDefault());

        mockMvc.perform(get("/person/"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}
