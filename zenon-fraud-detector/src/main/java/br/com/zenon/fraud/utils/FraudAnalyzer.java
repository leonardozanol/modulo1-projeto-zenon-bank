package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        transactions.stream().filter(Transaction::isFraud).sorted(Comparator.reverseOrder()).limit(3).forEach(transaction -> System.out.printf("%.2f%n", transaction.amount()));
    }

    public void getNameSuspiciousCustomers() {
        System.out.println("Clientes Suspeitos:");
        transactions.stream().filter(Transaction::isFraud).sorted(Comparator.reverseOrder()).limit(5).map(transaction -> transaction.origin().name()).distinct().forEach(System.out::println);
    }

    public void getTotalLoss() {
        System.out.println("Prejuízo Total: " + transactions.stream().filter(Transaction::isFraud).map(Transaction::amount).reduce(BigDecimal::add).orElse(new BigDecimal("0.0")));
    }

    public void getFraudByType() {
        System.out.println("Fraudes por Tipo:");
        transactions.stream().filter(Transaction::isFraud).collect(Collectors.groupingBy(Transaction::type)).forEach((type, trans) -> System.out.println(" - " + type.name() + ": " + trans.size()));
    }

}
