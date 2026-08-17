package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    private static List<Transaction> transactions;

    public TransactionListRepository(String fileName) {
        transactions = TransactionIngestor.read(fileName);
    }

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        return transactions.stream()
                .filter(transaction -> nameCustomer.equals(transaction.origin().name()))
                .findFirst();
    }

}
