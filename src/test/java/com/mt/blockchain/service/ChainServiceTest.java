package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.dto.BlockchainDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChainServiceTest {

    @Mock
    private ProofValidator proofValidator;

    @Mock
    private HashService hashService;

    @InjectMocks
    private ChainService chainService = new ChainService();


    @Test
    public void shouldValidChain() {
        // given
        BlockchainDto blockchain = new BlockchainDto();
        List<Block> blockList = new ArrayList<>();
        Block b = new Block();
        b.setId(1L);
        b.setPreviousHash("1");
        Block b2 = new Block();
        b2.setId(2L);
        b2.setPreviousHash("1");
        Block b3 = new Block();
        b3.setId(3L);
        b3.setPreviousHash("1");
        blockList.add(b);
        blockList.add(b2);
        blockList.add(b3);
        blockchain.setChain(blockList);
        when(hashService.hash(any())).thenReturn("1");
        when(proofValidator.validateProof(any(), any())).thenReturn(true);
        // when
        Boolean result = chainService.validChain(blockchain);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldValidChainReturnFalseDiffHash() {
        // given
        BlockchainDto blockchain = new BlockchainDto();
        List<Block> blockList = new ArrayList<>();
        Block b = new Block();
        b.setId(1L);
        b.setPreviousHash("1");
        Block b2 = new Block();
        b2.setId(2L);
        b2.setPreviousHash("2");
        Block b3 = new Block();
        b3.setId(3L);
        b3.setPreviousHash("3");
        blockList.add(b);
        blockList.add(b2);
        blockList.add(b3);
        blockchain.setChain(blockList);
        when(hashService.hash(any())).thenReturn("1");
        // when
        Boolean result = chainService.validChain(blockchain);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldValidChainReturnFalse() {
        // given
        BlockchainDto blockchain = new BlockchainDto();

        // when
        Boolean result = chainService.validChain(blockchain);

        // then
        assertFalse(result);
    }

}