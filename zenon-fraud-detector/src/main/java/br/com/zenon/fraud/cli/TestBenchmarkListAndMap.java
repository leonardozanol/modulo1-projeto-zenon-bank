package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionListRepository;

import java.util.Optional;

public class TestBenchmarkListAndMap {

    private static final TransactionListRepository transactionListRepository = new TransactionListRepository("data/PS_20174392719_1491204439457_log.csv");

    static void main() {
        showTransactionByNameCustomer("C12345");
        showTransactionByNameCustomer("C1231006815");

        long startTime = System.nanoTime();
        transactionListRepository.getByNameCustomer("C1868032458");
        long endTime = System.nanoTime();

        System.out.println("Tempo Benchmark: " + (endTime - startTime));

    }

    private static void showTransactionByNameCustomer(String nameCustomer) {
        Optional<Transaction> transaction = transactionListRepository.getByNameCustomer(nameCustomer);
        if (transaction.isPresent()) {
            System.out.println("Transação Encontrada:\n - " + transaction.get());

        } else {
            System.out.println("Transação não econtrada para o cliente: " + nameCustomer);
        }
    }
    
}
