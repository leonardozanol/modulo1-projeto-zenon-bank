package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionIngestor;

import java.io.File;
import java.util.List;

public class TestTransactionIngestor {

    static void main() {

        List<Transaction> transactions = TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        System.out.println("Size Transactions List: " + transactions.size());
        transactions.stream().limit(10).forEach(System.out::println);

    }

}
