package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.repositories.TransactionSQLRespository;
import br.com.zenon.fraud.utils.ingestor.TransactionIngestor;

import java.util.List;

public class IngestionMain {

    static void main() {

        TransactionSQLRespository repository = new TransactionSQLRespository();

        long startTime = System.nanoTime();

        List<Transaction> transactions = TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        System.out.println("Quantidade de Transações: " + transactions.size());

        repository.saveAll(transactions);

        long endTime = System.nanoTime();

        System.out.println("Tempo de ingestão: " + ((endTime - startTime) / 1000000) + " ms.");

    }

}
