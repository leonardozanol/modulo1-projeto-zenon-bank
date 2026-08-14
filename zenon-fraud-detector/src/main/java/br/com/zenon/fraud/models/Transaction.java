package br.com.zenon.fraud.models;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(Long step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer origin,
                          TransactionCustomer recipient,
                          boolean isFraud,
                          boolean isFlaggedFraud) {

    public Transaction {
        Objects.requireNonNull(step);
        Objects.requireNonNull(type);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(recipient);


        if (step < 1) throw new NumberFormatException("Error step must be greater than or equal to 1");
        if (amount.signum() < 0) throw new IllegalArgumentException("Error amount value cannot be negative " + amount);

    }

}
