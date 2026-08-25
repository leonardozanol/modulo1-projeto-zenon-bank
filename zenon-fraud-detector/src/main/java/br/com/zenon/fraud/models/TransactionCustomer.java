package br.com.zenon.fraud.models;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionCustomer(String name,
                                  BigDecimal oldBalance,
                                  BigDecimal newBalance) {

    public TransactionCustomer {
        Objects.requireNonNull(name);
        Objects.requireNonNull(oldBalance);
        Objects.requireNonNull(newBalance);

        if (name.trim().isEmpty()) throw new IllegalArgumentException("Error name not be empty");
        if (oldBalance.signum() < 0) throw new IllegalArgumentException("Error old balance value cannot be negative " + oldBalance);
        if (newBalance.signum() < 0) throw new IllegalArgumentException("Error new balance value cannot be negative " + newBalance);

    }

}
