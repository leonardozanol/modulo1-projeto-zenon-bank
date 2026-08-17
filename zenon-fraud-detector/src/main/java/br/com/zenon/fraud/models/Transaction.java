package br.com.zenon.fraud.models;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(Long step,
                          TransactionType type,
                          BigDecimal amount,
                          TransactionCustomer origin,
                          TransactionCustomer recipient,
                          boolean isFraud,
                          boolean isFlaggedFraud) implements Comparable<Transaction> {

    public Transaction {
        Objects.requireNonNull(step);
        Objects.requireNonNull(type);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(origin);
        Objects.requireNonNull(recipient);


        if (step < 1) throw new NumberFormatException("Error step must be greater than or equal to 1");
        if (amount.signum() < 0) throw new IllegalArgumentException("Error amount value cannot be negative " + amount);

    }

    @Override
    public int compareTo(Transaction o) {
        return amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return isFraud == that.isFraud && isFlaggedFraud == that.isFlaggedFraud && Objects.equals(step, that.step) && Objects.equals(amount, that.amount) && type == that.type && Objects.equals(origin, that.origin) && Objects.equals(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
    }

}
