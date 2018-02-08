package com.mt.blockchain.service;

import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.Transaction;
import com.mt.blockchain.model.dto.TransactionInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private static final String TRANSACTION_ARGUMENTS_ARE_MISSING = "Transaction arguments are missing";

    @Autowired
    private Blockchain blockchain;

    public Long newTransaction(Transaction t) {
        if (t != null) {
            blockchain.getCurrentTransactions().add(t);
            return blockchain.lastBlock().getId() + 1L;
        }
        return 0L;
    }

    public Long addTransaction(TransactionInfoDto info) {
        return newTransaction(Optional.ofNullable(info).map(this::convertToTransaction).orElse(null));
    }

    private Transaction convertToTransaction(TransactionInfoDto info) {
        if (StringUtils.isNotEmpty(info.getSender())
                && StringUtils.isNotEmpty(info.getRecipient())
                && StringUtils.isNotEmpty(Optional.ofNullable(info.getAmount()).map(Object::toString).orElse(null))) {
            return new Transaction(info.getSender(), info.getRecipient(), info.getAmount());
        }
        throw new IllegalArgumentException(TRANSACTION_ARGUMENTS_ARE_MISSING);
    }
}
