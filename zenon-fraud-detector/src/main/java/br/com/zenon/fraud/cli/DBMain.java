package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.ingestor.TransactionIngestor;
import br.com.zenon.fraud.repositories.TransactionSQLRespository;

import java.util.List;
import java.util.Optional;

public class DBMain {

    static void main(String[] args) {

        TransactionSQLRespository respository = new TransactionSQLRespository();

        if (args.length > 0 && (args[0].equals("-save") || args[0].equals("--save"))) {
            long timeStart, timeEnd = 0;

            timeStart = System.currentTimeMillis();
            List<Transaction> transactions = TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
            //TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv").forEach(respository::save);
            respository.saveAll(transactions);
            timeEnd = System.currentTimeMillis();

            System.out.println("Tempo De Execução: " + (timeEnd - timeStart) + " ms.");
        }

        System.out.println("Buscando Transação pelo Cliente: 'C1231006815'");
        Optional<Transaction> transaction = respository.getByNameCustomer("C1231006815");
        if (transaction.isPresent()) {
            System.out.println("Transação Encontrada: " + transaction.get());
        } else {
            System.out.println("Transação Não Econtrada: 'C1231006815'");
        }

        System.out.println("Buscando Transação pelo Cliente: 'C12345'");
        transaction = respository.getByNameCustomer("C12345");
        if (transaction.isPresent()) {
            System.out.println("Transação Encontrada: " + transaction.get());
        } else {
            System.out.println("Transação Não Econtrada: 'C12345'");
        }

    }

}
