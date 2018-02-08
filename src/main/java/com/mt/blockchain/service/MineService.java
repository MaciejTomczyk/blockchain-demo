package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.Transaction;
import com.mt.blockchain.model.dto.BlockInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MineService {

    private static final String NEW_BLOCK_FORGED = "New Block Forged";
    private static final String SENDER = "0";
    private static final String HYPHEN = "-";
    private static final String EMPTY = "";
    private static final double AMOUNT = 1D;

    @Autowired
    private Blockchain blockchain;

    @Autowired
    private ProofValidator proofValidator;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private HashService hashService;

    @Autowired
    private BlockService blockService;


    public BlockInfoDto mineBlock() {
        Block lastBlock = blockchain.lastBlock();
        Long lastProof = lastBlock.getProof();
        Long proof = proofValidator.proofOfWork(lastProof);
        String uuid = UUID.randomUUID().toString().replace(HYPHEN, EMPTY);
        transactionService.newTransaction(new Transaction(SENDER, uuid, AMOUNT));
        String previousHash = hashService.hash(lastBlock);

        Block newBlock = blockService.newBlock(blockchain, proof, previousHash);
        return new BlockInfoDto(NEW_BLOCK_FORGED, newBlock.getId(), newBlock.getTransactions(), newBlock.getProof(), newBlock.getPreviousHash());
    }


}
