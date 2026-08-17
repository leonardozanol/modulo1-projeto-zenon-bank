package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class FraudAnalyzer {

    private static List<Transaction> transactions;

    public FraudAnalyzer(String fileName) {
        transactions = TransactionIngestor.read(fileName);
    }

    public void getTotalFraud() {
        System.out.println("Total de Fraudes: " + transactions.stream().filter(Transaction::isFraud).count());
    }

    public void getTopHighestAmount() {
        System.out.println("Top 3 Fraudes de Maior Valor:");
        transactions.stream().filter(Transaction::isFraud).sorted(Comparator.reverseOrder()).limit(3).forEach(transaction -> {
            System.out.printf("%.2f%n", transaction.amount());
        });
    }

}
