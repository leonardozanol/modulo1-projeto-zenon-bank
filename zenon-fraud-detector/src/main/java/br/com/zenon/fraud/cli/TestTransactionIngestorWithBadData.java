package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionIngestor;

import java.util.List;

public class TestTransactionIngestorWithBadData {

    static void main() {

        List<Transaction> transactions = TransactionIngestor.read("data/paysim_with_bad_data.csv");

        System.out.println("Transactions Size: " + transactions.size());
        transactions.forEach(System.out::println);

    }

}
