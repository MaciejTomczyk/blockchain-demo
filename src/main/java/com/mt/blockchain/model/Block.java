package com.mt.blockchain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "timestamp", "transactions", "proof", "previousHash"})
public class Block {

    private Long id;
    private Instant timestamp;
    private List<Transaction> transactions;
    private Long proof;
    private String previousHash;
}
