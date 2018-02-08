package com.mt.blockchain.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProofValidatorTest {

    private ProofValidator proofValidator = new ProofValidator();

    @Test
    public void proofOfWork() {
        // given
        Long lastProof = 100L;

        // when
        Long result = proofValidator.proofOfWork(lastProof);

        // then
        assertEquals(33575L, result, 123123123L);
    }

}