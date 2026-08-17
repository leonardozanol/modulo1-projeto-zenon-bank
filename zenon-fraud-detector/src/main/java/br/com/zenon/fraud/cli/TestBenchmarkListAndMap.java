package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionListRepository;
import br.com.zenon.fraud.utils.TransactionMapRepository;
import br.com.zenon.fraud.utils.TransactionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TestBenchmarkListAndMap {

    private static final TransactionListRepository transactionListRepository = new TransactionListRepository("data/PS_20174392719_1491204439457_log.csv");
    private static final TransactionMapRepository transactionMapRepository = new TransactionMapRepository("data/PS_20174392719_1491204439457_log.csv");

    static void main() {
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
            System.out.println(" - Transação Encontrada:\n - " + transaction.get());

        } else {
            System.out.println(" - Transação não econtrada para o cliente: " + nameCustomer);
        }
    }

    private static void calcTimeBenchmark(String nameCustomer, TransactionRepository transactionRepository) {
        long startTime = System.nanoTime();
        transactionRepository.getByNameCustomer(nameCustomer);
        long endTime = System.nanoTime();

        System.out.println(" - Tempo Benchmark: " + (endTime - startTime));
    }
    
}
