package br.com.zenon.fraud.utils.repository;

import br.com.zenon.fraud.models.Transaction;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> getByNameCustomer(String nameCustomer);

    boolean save(Transaction transaction);

}

