package br.com.zenon.fraud.models;

import java.math.BigDecimal;

public record Transaction(Long step,
                          TransactionType type,
                          BigDecimal amount,
                          Customer orig,
                          Customer dest,
                          boolean isFraud,
                          boolean isFlaggedFraud) {
}
