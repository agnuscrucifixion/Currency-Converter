package com.example.accounts.services;



import com.fasterxml.jackson.core.JsonProcessingException;


import converter.ConverterServiceGrpc;
import converter.Converter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Service
public class ConverterControllerGrpcClient {
    private final ConverterServiceGrpc.ConverterServiceBlockingStub converterServiceBlockingStub;
    @Autowired
    public ConverterControllerGrpcClient(ConverterServiceGrpc.ConverterServiceBlockingStub converterServiceBlockingStub) {
        this.converterServiceBlockingStub = converterServiceBlockingStub;
    }
    @CircuitBreaker(name = "converterBreaker")
    converter.ConvertResponse convert(@RequestParam String from, @RequestParam String to, @RequestParam BigDecimal amount) throws JsonProcessingException {
        return converterServiceBlockingStub.convert(converter.ConvertRequest
                .newBuilder()
                .setFrom(from)
                .setTo(to)
                .setAmount(amount.doubleValue())
                .build());
    }
}
