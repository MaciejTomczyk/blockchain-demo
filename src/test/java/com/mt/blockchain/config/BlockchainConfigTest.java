package com.mt.blockchain.config;

import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.service.BlockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BlockchainConfigTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private BlockchainConfig config = new BlockchainConfig();

    @Test
    public void getBlockchain() {
        // given

        // when
        Blockchain result = config.getBlockchain();

        // then
        verify(blockService, atLeastOnce()).newBlock(any(), any(), any());
        assertNotNull(result);

    }
}