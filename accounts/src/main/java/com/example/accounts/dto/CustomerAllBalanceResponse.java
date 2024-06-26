package com.example.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
public class CustomerAllBalanceResponse {
    private BigDecimal balance;
    private String currency;
}
