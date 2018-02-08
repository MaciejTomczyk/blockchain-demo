package com.mt.blockchain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"sender", "recipient", "amount"})
public class Transaction {

    private String sender;
    private String recipient;
    private Double amount;
}
