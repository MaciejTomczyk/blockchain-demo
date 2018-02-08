package com.mt.blockchain.service;

import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.dto.BlockchainDto;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
public class NodeService {

    private static final String CHAIN = "chain";
    private static final String PATH = "/";

    @Autowired
    private Blockchain blockchain;

    @Autowired
    private ChainService chainService;

    private RestTemplate restTemplate = new RestTemplate();


    public int registerNodes(List<String> url) {
        url.forEach(i -> blockchain.getNodes().add(i));
        return blockchain.getNodes().size();
    }

    public Boolean resolveConflicts() {
        BlockchainDto newChain;
        List<BlockchainDto> foundChains = new ArrayList<>();
        blockchain.getNodes().forEach(i -> {
            try {
                Optional<BlockchainDto> response = Optional.ofNullable(restTemplate.getForObject(i + PATH + CHAIN, BlockchainDto.class));
                if (response.isPresent() && chainService.validChain(response.get())) {
                    foundChains.add(response.get());
                }
            } catch (RestClientException e) {
                log.warn(e.getMessage(), e);
            }
        });
        newChain = foundChains.stream().max(Comparator.comparingInt(i -> i.getChain().size())).orElse(null);
        return Optional.ofNullable(newChain).map(this::changeChain).orElse(false);
    }

    @SuppressWarnings("SameReturnValue")
    private Boolean changeChain(BlockchainDto newChain) {
        blockchain.setChain(newChain.getChain());
        return true;
    }

    public BlockchainDto getBlockchain() {
        return new BlockchainDto(blockchain.getChain());
    }

}
