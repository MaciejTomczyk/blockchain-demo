package com.mt.blockchain.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.blockchain.application.Application;
import com.mt.blockchain.model.dto.TransactionInfoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BlockchainControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldMine() throws Exception {
        mockMvc.perform(get("/mine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("New Block Forged"));
    }

    @Test
    public void shouldCreateNewTransaction() throws Exception {
        TransactionInfoDto info = new TransactionInfoDto();
        info.setSender("sender");
        info.setRecipient("recipient");
        info.setAmount(1D);
        mockMvc.perform(post("/transactions/new").content(mapper.writeValueAsString(info)).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void getChain() throws Exception {
        mockMvc.perform(get("/chain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chain").exists());
    }

    @Test
    public void registerNodes() throws Exception {
        mockMvc.perform(post("/nodes/register").content(mapper.writeValueAsString(Collections.singletonList("localhost:8080"))).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void resolveConflicts() throws Exception {
        mockMvc.perform(get("/nodes/resolve"))
                .andExpect(status().isOk());
    }
}