package com.mt.blockchain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.blockchain.model.Block;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class HashService {

    @Autowired
    private ObjectMapper mapper;

    public String hash(Block block) {
        try {
            return DigestUtils.sha256Hex(mapper.writeValueAsString(block));
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }
}
