package com.mt.blockchain.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class ProofValidator {

    private static final String ANSWER = "0000";

    public Long proofOfWork(Long lastProof) {
        Long proof = 0L;
        while (!validateProof(lastProof, proof)) {
            proof += 1;
        }
        return proof;
    }

    public Boolean validateProof(Long lastProof, Long proof) {
        String guess = lastProof.toString() + proof.toString();
        String hash = DigestUtils.sha256Hex(guess);
        return hash.substring(hash.length() - 4).equals(ANSWER);
    }
}
