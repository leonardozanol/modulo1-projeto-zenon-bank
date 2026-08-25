package br.com.zenon.fraud.repositories;

import br.com.zenon.fraud.models.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        return transactions.stream()
                .filter(transaction -> nameCustomer.equals(transaction.origin().name()))
                .findFirst();
    }

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

}
