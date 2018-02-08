package com.mt.blockchain.service;

import com.mt.blockchain.model.Block;
import com.mt.blockchain.model.Blockchain;
import com.mt.blockchain.model.dto.BlockchainDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NodeServiceTest {


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Blockchain blockchain;

    @Mock
    private ChainService chainService;

    @Mock
    private RestTemplate restTemplate = new RestTemplate();

    @InjectMocks
    private NodeService nodeService = new NodeService();


    @Test
    public void shouldRegisterNodes() {
        // given
        List<String> urls = Collections.singletonList("localhost:8080");
        when(blockchain.getNodes().size()).thenReturn(1);

        // when
        int result = nodeService.registerNodes(urls);

        // then
        assertEquals(1, result);
    }

    @Test
    public void shouldResolveConflicts() {
        // given
        Set<String> nodes = Set.of("http://localhost:8080", "http://localhost:8081");
        BlockchainDto chain = new BlockchainDto();
        chain.setChain(Arrays.asList(new Block(), new Block()));
        when(blockchain.getNodes()).thenReturn(nodes);
        when(restTemplate.getForObject("http://localhost:8080/chain", BlockchainDto.class)).thenReturn(chain);
        when(restTemplate.getForObject("http://localhost:8081/chain", BlockchainDto.class)).thenReturn(chain);
        when(chainService.validChain(any())).thenReturn(true);

        // when
        Boolean result = nodeService.resolveConflicts();

        // then
        assertTrue(result);
    }

    @Test
    public void shouldResolveConflictsInvalidChain() {
        // given
        Set<String> nodes = Set.of("http://localhost:8080", "http://localhost:8081");
        BlockchainDto chain = new BlockchainDto();
        chain.setChain(Arrays.asList(new Block(), new Block()));
        when(blockchain.getNodes()).thenReturn(nodes);
        when(restTemplate.getForObject("http://localhost:8080/chain", BlockchainDto.class)).thenReturn(chain);
        when(restTemplate.getForObject("http://localhost:8081/chain", BlockchainDto.class)).thenReturn(chain);
        when(chainService.validChain(any())).thenReturn(false);

        // when
        Boolean result = nodeService.resolveConflicts();

        // then
        assertFalse(result);
    }

    @Test
    public void shouldResolveConflictsThrowException() {
        // given
        Set<String> nodes = Set.of("http://localhost:8080", "http://localhost:8081");
        BlockchainDto chain = new BlockchainDto();
        chain.setChain(Arrays.asList(new Block(), new Block()));
        when(blockchain.getNodes()).thenReturn(nodes);
        when(restTemplate.getForObject("http://localhost:8080/chain", BlockchainDto.class)).thenThrow(RestClientException.class);

        // when
        Boolean result = nodeService.resolveConflicts();

        // then
        assertFalse(result);
    }

    @Test
    public void getBlockchain() {
        // given
        List<Block> chain = new ArrayList<>();
        when(blockchain.getChain()).thenReturn(chain);

        // when
        BlockchainDto result = nodeService.getBlockchain();

        // then
        assertThat(result.getChain(), is(chain));
    }
}