package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.dto.BlockInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MineServiceTest {

    @Mock
    private Blockchain blockchain;

    @Mock
    private ProofValidator proofValidator;

    @Mock
    private TransactionService transactionService;

    @Mock
    private HashService hashService;

    @Mock
    private BlockService blockService;

    @InjectMocks
    private MineService mineService = new MineService();

    @Test
    public void mineBlock() {
        // given
        Block b = new Block();
        Block newBlock = new Block();
        newBlock.setId(1L);
        b.setProof(100L);
        Long proof = 123L;
        String previousHash = "ph";
        when(blockchain.lastBlock()).thenReturn(b);
        when(proofValidator.proofOfWork(b.getProof())).thenReturn(proof);
        when(transactionService.newTransaction(any())).thenReturn(1L);
        when(hashService.hash(b)).thenReturn(previousHash);
        when(blockService.newBlock(blockchain, proof, previousHash)).thenReturn(newBlock);

        // when
        BlockInfoDto result = mineService.mineBlock();

        // then
        verify(blockchain, atLeastOnce()).lastBlock();
        verify(proofValidator, atLeastOnce()).proofOfWork(b.getProof());
        verify(transactionService, atLeastOnce()).newTransaction(any());
        verify(hashService, atLeastOnce()).hash(b);
        verify(blockService, atLeastOnce()).newBlock(blockchain, proof, previousHash);
        assertEquals(1L, result.getId(), 123123123L);
    }
}