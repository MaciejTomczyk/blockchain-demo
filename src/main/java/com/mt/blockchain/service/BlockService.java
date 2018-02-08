package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BlockService {

    @Autowired
    private HashService hashService;

    public Block newBlock(Blockchain blockchain, Long proof, String previousHash) {
        Block b = new Block();
        b.setId((long) (blockchain.getChain().size() + 1));
        b.setTimestamp(Instant.now());
        b.setProof(proof);
        Optional.ofNullable(blockchain.getCurrentTransactions()).ifPresent(i -> b.setTransactions(new ArrayList<>(i)));
        Optional.ofNullable(previousHash).ifPresentOrElse(b::setPreviousHash, () -> fillHash(b, blockchain));
        blockchain.getCurrentTransactions().clear();
        blockchain.getChain().add(b);
        return b;
    }

    private void fillHash(Block b, Blockchain blockchain) {
        b.setPreviousHash(hashService.hash(blockchain.getChain().get(blockchain.getChain().size() - 1)));
    }
}
