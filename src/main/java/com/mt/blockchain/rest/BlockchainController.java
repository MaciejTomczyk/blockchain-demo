package com.mt.blockchain.rest;

import com.mt.blockchain.model.dto.TransactionInfoDto;
import com.mt.blockchain.service.MineService;
import com.mt.blockchain.service.NodeService;
import com.mt.blockchain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlockchainController {

    @Autowired
    private MineService mineService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private NodeService nodeService;

    @GetMapping(value = "/mine")
    public ResponseEntity mine() {
        return ResponseEntity.ok().body(mineService.mineBlock());
    }

    @PostMapping(value = "/transactions/new")
    public ResponseEntity newTransaction(@RequestBody TransactionInfoDto transaction) {
        return ResponseEntity.ok().body(transactionService.addTransaction(transaction));
    }

    @GetMapping(value = "/chain")
    public ResponseEntity getChain() {
        return ResponseEntity.ok().body(nodeService.getBlockchain());
    }

    @PostMapping(value = "/nodes/register")
    public ResponseEntity registerNodes(@RequestBody List<String> nodes) {
        return ResponseEntity.ok().body(nodeService.registerNodes(nodes));
    }

    @GetMapping(value = "/nodes/resolve")
    public ResponseEntity resolveConflicts() {
        return ResponseEntity.ok().body(nodeService.resolveConflicts());
    }
}
