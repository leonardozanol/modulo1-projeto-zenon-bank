package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionIngestor;
import br.com.zenon.fraud.utils.repository.TransactionListRepository;
import br.com.zenon.fraud.utils.repository.TransactionMapRepository;
import br.com.zenon.fraud.utils.repository.TransactionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestBenchmarkListAndMap {

    static void main() {

        List<Transaction> transactionsList = TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        TransactionListRepository transactionListRepository = new TransactionListRepository(transactionsList);
        TransactionMapRepository transactionMapRepository = new TransactionMapRepository(transactionsList);

        System.out.println("Utilizando List Repository:");
        showTransactionByNameCustomer("C12345", transactionListRepository);
        showTransactionByNameCustomer("C1231006815", transactionListRepository);

        System.out.println("Utilizando Map Repository:");
        showTransactionByNameCustomer("C12345", transactionMapRepository);
        showTransactionByNameCustomer("C1231006815", transactionMapRepository);

        System.out.println("Benchmark LIST:");
        calcTimeBenchmark("C1868032458", transactionListRepository);

        System.out.println("Benchmark MAP:");
        calcTimeBenchmark("C1868032458", transactionMapRepository);

    }

    private static void showTransactionByNameCustomer(String nameCustomer, TransactionRepository transactionRepository) {
        Optional<Transaction> transaction = transactionRepository.getByNameCustomer(nameCustomer);
        if (transaction.isPresent()) {
            System.out.println(" - Transação Encontrada:\n   - " + transaction.get());

        } else {
            System.out.println(" - Transação não econtrada para o cliente: " + nameCustomer);
        }
    }

    private static void calcTimeBenchmark(String nameCustomer, TransactionRepository transactionRepository) {
        long startTime = System.nanoTime();
        transactionRepository.getByNameCustomer(nameCustomer);
        long endTime = System.nanoTime();

        System.out.println(" - Tempo Benchmark: " + (endTime - startTime) / 1_000_000.0 + "ms.");
    }
    
}
