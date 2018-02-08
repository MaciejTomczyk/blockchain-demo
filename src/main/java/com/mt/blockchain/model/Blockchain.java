package com.mt.blockchain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"chain", "currentTransactions"})
@CommonsLog
public class Blockchain {


    private List<Block> chain = new ArrayList<>();
    private List<Transaction> currentTransactions = new ArrayList<>();
    private Set<String> nodes = new HashSet<>();

    //This will not fail as we always create Blockchain with 1 element
    public Block lastBlock() {
        return chain.get(chain.size() - 1);
    }
}
