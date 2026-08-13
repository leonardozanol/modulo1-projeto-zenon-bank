package br.com.zenon.fraud.models;

import java.math.BigDecimal;

public record Transaction(Long step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer origin,
                          TransactionCustomer recipient,
                          boolean isFraud,
                          boolean isFlaggedFraud) {
}
