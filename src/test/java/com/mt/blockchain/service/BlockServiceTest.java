package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Blockchain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockServiceTest {

    @Mock
    private HashService hashService;

    @InjectMocks
    private BlockService blockService = new BlockService();

    @Test
    public void shouldCreateNewBlock() {
        // given
        Blockchain blockchain = new Blockchain();
        Block b = new Block();
        blockchain.setChain(new ArrayList<>());
        blockchain.getChain().add(b);
        when(hashService.hash(any())).thenReturn("");

        // when
        blockService.newBlock(blockchain, 100L, null);

        // then
        verify(hashService, atLeastOnce()).hash(any());
        assertEquals(2, blockchain.getChain().size());
    }
}