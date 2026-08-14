package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionIngestor;

import java.io.File;
import java.util.List;

public class TestTransactionIngestor {

    static void main() {

        List<Transaction> transactions = TransactionIngestor.processFile(new File("data/PS_20174392719_1491204439457_log.csv"));

        transactions.forEach(System.out::println);
        System.out.println("Size Transactions List: " + transactions.size());

    }

}
