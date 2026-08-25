package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.repositories.TransactionSQLRespository;
import br.com.zenon.fraud.utils.ingestor.EfficientTransactionIngestor;

public class IngestionMain {

    static void main() {

        TransactionSQLRespository repository = new TransactionSQLRespository();

        long startTime = System.nanoTime();

        EfficientTransactionIngestor.readAsBatch("data/PS_20174392719_1491204439457_log.csv", repository::saveAll);

        long endTime = System.nanoTime();

        System.out.println("Tempo de ingestão: " + ((endTime - startTime) / 1000000) + " ms.");

    }

}
