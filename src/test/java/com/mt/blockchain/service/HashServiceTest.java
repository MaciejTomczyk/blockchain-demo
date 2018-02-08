package com.mt.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.blockchain.model.Block;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HashServiceTest {

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private HashService hashService = new HashService();

    @Test
    public void shouldHash() throws JsonProcessingException {
        // given
        Block b = new Block();
        when(mapper.writeValueAsString(b)).thenReturn("");

        // when
        String result = hashService.hash(b);

        // then
        verify(mapper, atLeastOnce()).writeValueAsString(b);
        assertEquals(64, result.length());
    }

    @Test
    public void shouldHashAndThrowException() throws JsonProcessingException {
        // given
        Block b = new Block();
        when(mapper.writeValueAsString(b)).thenThrow(JsonProcessingException.class);

        // when
        String result = hashService.hash(b);

        // then
        verify(mapper, atLeastOnce()).writeValueAsString(b);
        assertNull(result);
    }
}