package br.com.zenon.fraud.utils.repository;

import br.com.zenon.fraud.models.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionMapRepository implements TransactionRepository {

    private final Map<String, Transaction> transactions;

    public TransactionMapRepository(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions.stream().collect(Collectors.toMap(
                transaction -> transaction.origin().name(),
                transaction -> transaction
        ));
    }

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        return Optional.ofNullable(transactions.get(nameCustomer));
    }

}
