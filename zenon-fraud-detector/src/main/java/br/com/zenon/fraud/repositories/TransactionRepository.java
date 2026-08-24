package br.com.zenon.fraud.repositories;

import br.com.zenon.fraud.models.Transaction;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> getByNameCustomer(String nameCustomer);

    void save(Transaction transaction);

}

