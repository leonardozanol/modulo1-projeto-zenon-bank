package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.TransactionListRepository;

import java.util.Optional;

public class TestBenchmarkListAndMap {

    static void main() {
        showTransactionByNameCustomer("C12345");
        showTransactionByNameCustomer("C1231006815");
    }

    private static void showTransactionByNameCustomer(String nameCustomer) {
        TransactionListRepository transactionListRepository = new TransactionListRepository("data/PS_20174392719_1491204439457_log.csv");

        Optional<Transaction> transaction = transactionListRepository.getByNameCustomer(nameCustomer);
        if (transaction.isPresent()) {
            System.out.println("Transação Encontrada:\n - " + transaction.get());

        } else {
            System.out.println("Transação não econtrada para o cliente: " + nameCustomer);
        }
    }
    
}
