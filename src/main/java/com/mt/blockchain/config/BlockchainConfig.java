package com.mt.blockchain.config;

import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockchainConfig {

    @Autowired
    private BlockService blockService;

    @Bean
    Blockchain getBlockchain() {
        Blockchain b = new Blockchain();
        blockService.newBlock(b, 100L, "1");
        return b;
    }
}
