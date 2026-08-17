package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> getByNameCustomer(String nameCustomer);

}

