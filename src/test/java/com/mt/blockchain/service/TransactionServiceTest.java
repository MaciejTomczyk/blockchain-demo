package com.mt.blockchain.service;

import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.Transaction;
import com.mt.blockchain.model.dto.TransactionInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Blockchain blockchain;

    @InjectMocks
    private TransactionService transactionService = new TransactionService();

    @Test
    public void shouldCreateNewTransaction() {
        // given
        Transaction t = new Transaction("sender", "recipient", 1D);
        when(blockchain.lastBlock().getId()).thenReturn(2L);
        // when
        Long result = transactionService.newTransaction(t);

        // then
        assertEquals(2L, result, 123123123L);
    }

    @Test
    public void shouldAddTransaction() {
        // given
        TransactionInfoDto info = new TransactionInfoDto();
        info.setSender("sender");
        info.setRecipient("recipient");
        info.setAmount(1D);
        when(blockchain.lastBlock().getId()).thenReturn(2L);

        // when
        Long result = transactionService.addTransaction(info);

        // then
        assertEquals(2L, result, 123123123L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAddNullTransactionNoSender() {
        // given
        TransactionInfoDto info = new TransactionInfoDto();
        info.setSender(null);
        info.setRecipient("recipient");
        info.setAmount(1D);

        // when
        transactionService.addTransaction(info);

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAddNullTransactionNoRecipient() {
        // given
        TransactionInfoDto info = new TransactionInfoDto();
        info.setSender("sender");
        info.setRecipient(null);
        info.setAmount(1D);

        // when
        transactionService.addTransaction(info);

        // then
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldAddNullTransactionNoAmount() {
        // given
        TransactionInfoDto info = new TransactionInfoDto();
        info.setSender("sender");
        info.setRecipient("recipient");
        info.setAmount(null);

        // when
        transactionService.addTransaction(info);

        // then
    }

    @Test
    public void shouldAddNullTransactionThrowException() {
        // given

        // when
        Long result = transactionService.addTransaction(null);

        // then
        assertEquals(0L, result, 123123123L);
    }
}