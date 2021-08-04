package io.applianceskeeper.appliances.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.applianceskeeper.AppliancesKeeperApplication;
import io.applianceskeeper.appliances.model.Model;
import io.applianceskeeper.appliances.service.ModelsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModelsController.class)
@ContextConfiguration(classes = {AppliancesKeeperApplication.class})
class ModelsControllerTest {

    private static ObjectMapper objectMapper;
    private static WebMvcLinkBuilder baseLink;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelsService service;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        baseLink = linkTo(ModelsController.class);
    }

    @Test
    void shouldReturnAllModels() throws Exception {
        //given
        var modelsList = List.of(new Model(), new Model(), new Model());
        given(service.findAll()).willReturn(modelsList);
        MockHttpServletRequestBuilder requestBuilder = get(baseLink.toUri());

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void shouldReturnAllModelsByName() throws Exception {
        //given
        var modelsList = List.of(new Model(), new Model(), new Model());
        var content = objectMapper.writeValueAsString(modelsList);
        given(service.findAllByName(anyString())).willReturn(modelsList);
        MockHttpServletRequestBuilder requestBuilder = get(baseLink.toUri()).param("name", "test");

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(content))
        ;
    }

}
