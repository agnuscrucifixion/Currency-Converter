package com.example.accounts.services;

import com.example.accounts.config.AccountsConfigEnv;
import com.example.accounts.dto.AmountDTO;
import com.example.accounts.dto.MessageDTO;
import com.example.accounts.entities.Account;
import com.example.accounts.entities.Message;
import com.example.accounts.exceptions.OutBoxSendException;
import com.example.accounts.repositories.OutboxMessageRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class OutboxService {

    private final RestTemplate restTemplate;
    private final AccountsConfigEnv accountsConfigEnv;
    private final OutboxMessageRepository outboxMessageRepository;
    public OutboxService(RestTemplate restTemplate, AccountsConfigEnv accountsConfigEnv,
                         OutboxMessageRepository outboxMessageRepository) {
        this.restTemplate = restTemplate;
        this.accountsConfigEnv = accountsConfigEnv;
        this.outboxMessageRepository = outboxMessageRepository;
    }
    public boolean outboxSendMessage(MessageDTO message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(message, headers);

        var responseEntity = restTemplate.exchange(
                accountsConfigEnv.getNotificationServiceUrl() + "/notification",
                HttpMethod.POST,
                request,
                Void.class);

        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    public void save(Account getAcc, BigDecimal amount) {
        Message message = new Message();
        message.setAccountNumber(getAcc.getAccountNumber());
        message.setAmount(amount);
        message.setBalance(getAcc.getBalance());
        outboxMessageRepository.save(message);
    }
}
