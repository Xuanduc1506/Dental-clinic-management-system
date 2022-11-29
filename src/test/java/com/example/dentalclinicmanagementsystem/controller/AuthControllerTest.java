package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.TokenDTO;
import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import com.example.dentalclinicmanagementsystem.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("Login")
    class TestLogin {
        @Test
        @DisplayName("Login success")
        void loginSuccess() throws Exception {

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setJwt("token string");
            tokenDTO.setRole("role");
            when(tokenProvider.createToken(any(Authentication.class))).thenReturn(tokenDTO);
            String user = "{\n" +
                    "    \"userName\":\"x1\",\n" +
                    "    \"password\":\"123\"\n" +
                    "}";
            mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(user))
                    .andExpect(status().isOk())
                    .andExpect(content().string("{" +
                            "\"jwt\":\"token string\"," +
                            "\"role\":\"role\"" +
                            "}"));
        }

        @Test
        @DisplayName("UserName null")
        void userNameNull() throws Exception {

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setJwt("token string");
            tokenDTO.setRole("role");
            when(tokenProvider.createToken(any(Authentication.class))).thenReturn(tokenDTO);
            String user = "{\n" +
                    "    \"password\":\"123\"\n" +
                    "}";
            mockMvc.perform(post("/api/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(user))
                    .andExpect(status().isBadRequest()).andExpect(content()
                            .string("[{\"message\":\"must not be blank\"," +
                                    "\"fieldName\":\"userName\"}]"));
        }

        @Test
        @DisplayName("Password null")
        void passwordNull() throws Exception {

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setJwt("token string");
            tokenDTO.setRole("role");
            when(tokenProvider.createToken(any(Authentication.class))).thenReturn(tokenDTO);
            String user = "{\n" +
                    "    \"userName\":\"x1\"\n" +
                    "}";
            mockMvc.perform(post("/api/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(user))
                    .andExpect(status().isBadRequest()).andExpect(content()
                            .string("[{\"message\":\"must not be blank\"," +
                                    "\"fieldName\":\"password\"}]"));
        }

        @Test
        @DisplayName("Password null")
        void wrongUsernameOrPasswordNull() throws Exception {

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setJwt("token string");
            tokenDTO.setRole("role");

            doThrow(new AuthenticationException())
            when(tokenProvider.createToken(any(Authentication.class))).thenReturn(tokenDTO);
            String user = "{\n" +
                    "    \"userName\":\"x1\"\n" +
                    "}";
            mockMvc.perform(post("/api/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(user))
                    .andExpect(status().isBadRequest()).andExpect(content()
                            .string("[{\"message\":\"must not be blank\"," +
                                    "\"fieldName\":\"password\"}]"));
        }

    }

}