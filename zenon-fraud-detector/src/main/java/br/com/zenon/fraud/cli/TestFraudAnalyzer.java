package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.utils.FraudAnalyzer;
import br.com.zenon.fraud.utils.ingestor.TransactionIngestor;

import java.util.List;

public class TestFraudAnalyzer {

    static void main() {

        List<Transaction> transactions = TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);

        System.out.println("Total de Fraudes: " + fraudAnalyzer.getTotalFraud());

        System.out.println("Top 3 Fraudes de Maior Valor:");
        fraudAnalyzer.getTopHighestAmount().forEach(value -> {
            System.out.printf(" - %.2f%n", value);
        });

        System.out.println("Clientes Suspeitos:");
        fraudAnalyzer.getNameSuspiciousCustomers().forEach(name -> {
            System.out.println(" - " + name);
        });

        System.out.println("Prejuízo Total: " + fraudAnalyzer.getTotalLoss());

        System.out.println("Fraudes por Tipo:");
        fraudAnalyzer.getFraudByType().forEach((type, count) -> {
            System.out.println(type + ": " + count);
        });

    }

}