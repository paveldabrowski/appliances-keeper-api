package io.applianceskeeper.commissions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.applianceskeeper.AppliancesKeeperApplication;
import io.applianceskeeper.clients.models.Client;
import io.applianceskeeper.commissions.data.CommissionsRepository;
import io.applianceskeeper.commissions.models.Commission;
import io.applianceskeeper.commissions.service.CommissionService;
import javassist.NotFoundException;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommissionsController.class)
@ContextConfiguration(classes = {AppliancesKeeperApplication.class})
class CommissionsControllerTest {

    private static ObjectMapper objectMapper;
    private static WebMvcLinkBuilder baseLink;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommissionService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        baseLink = linkTo(CommissionsController.class);
    }

    @Test
    void shouldUpdateCommission() throws Exception {
        //given
        Commission commission = new Commission();
        commission.setId(4L);
        doReturn(commission).when(service).save(commission);

        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(commission);
        RequestBuilder requestBuilder = patch(baseLink.slash("4").toUri())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson);

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(requestJson))
        ;
    }

    @Test
    void shouldReturnAllCommissions() throws Exception {
        //given
        RequestBuilder requestBuilder = get(baseLink.toUri()).contentType(MediaType.APPLICATION_JSON_VALUE);

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void shouldReturnCommissionById() throws Exception {
        //given
        RequestBuilder requestBuilder = get(baseLink.slash(5).toUri());

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotFoundCommissionById() throws Exception {
        //given
        RequestBuilder requestBuilder = get(baseLink.slash(7).toUri());
        given(service.findById(anyLong())).willThrow(NotFoundException.class);

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllCommissionsBelongsToClient() throws Exception {
        //given
        Client client = new Client();
        client.setId(1L);
        RequestBuilder requestBuilder = get(baseLink.slash("/client/" + client.getId()).toUri());

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnEmptyCommissionListWhenClientNotFoundOrHasNoCommissions() throws Exception {
        //given
        Client client = new Client();
        client.setId(72131321231L);
        RequestBuilder requestBuilder = get(baseLink.slash("/client/" + client.getId()).toUri());
        given(service.getAllCommissionsByClientId(anyLong())).willThrow(NotFoundException.class);

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void shouldReturnSortedFilteredPaginatedCommissionList() throws Exception {
        //given
        RequestBuilder requestBuilder = get(baseLink.toUri())
                .param("searchTerm", "")
                .param("page", "2")
                .param("sort", "id");

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCommissionsCount() throws Exception {
        //given
        given(service.getCommissionsCount()).willReturn(4L);
        RequestBuilder requestBuilder = get(baseLink.slash("count").toUri());

        //when
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddCommission() throws Exception {
        //given
        Commission commission = new Commission();
        String content = objectMapper.writeValueAsString(commission);
        given(service.save(any(Commission.class))).willReturn(commission);
        RequestBuilder requestBuilder = post("/commissions").contentType(MediaType.APPLICATION_JSON_VALUE).content(content);

        //when + then
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(content().json(content))
                .andExpect(status().isCreated());
    }
}
