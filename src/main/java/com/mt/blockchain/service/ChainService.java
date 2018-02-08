package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Pair;
import com.mt.blockchain.model.dto.BlockchainDto;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChainService {

    @Autowired
    private ProofValidator proofValidator;

    @Autowired
    private HashService hashService;

    public Boolean validChain(BlockchainDto dto) {
        List<Pair> pairs = prepareBlockPairs(dto);
        return pairs.isEmpty() ? false : checkPairs(pairs);
    }

    private List<Pair> prepareBlockPairs(BlockchainDto dto) {
        List<Pair> pairs = new ArrayList<>();
        Optional.ofNullable(dto.getChain()).ifPresent(i -> fillPairs(pairs, i));
        return pairs;
    }

    private void fillPairs(List<Pair> pairs, List<Block> blocks) {
        blocks.stream().skip(1).forEach(i -> {
            Pair p = new Pair(i, blocks.get(i.getId().intValue() - 2));
            pairs.add(p);
        });
    }

    private Boolean checkPairs(List<Pair> pairs) {
        return pairs.stream().map(this::checkPair).noneMatch(BooleanUtils::isFalse);
    }

    private Boolean checkPair(Pair pair) {
        if (!pair.getCurrent().getPreviousHash().equals(hashService.hash(pair.getPrevious()))) {
            return false;
        }
        return proofValidator.validateProof(pair.getPrevious().getProof(), pair.getCurrent().getProof());
    }


}
