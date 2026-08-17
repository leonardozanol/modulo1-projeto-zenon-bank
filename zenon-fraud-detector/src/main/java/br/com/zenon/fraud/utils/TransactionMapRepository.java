package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.util.Map;
import java.util.Optional;

public class TransactionMapRepository implements TransactionRepository {

    private static Map<String, Transaction> transactions;

    public TransactionMapRepository(String nameFile) {
        transactions = TransactionIngestor.readToMap(nameFile);
    }

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        return Optional.ofNullable(transactions.get(nameCustomer));
    }

}
