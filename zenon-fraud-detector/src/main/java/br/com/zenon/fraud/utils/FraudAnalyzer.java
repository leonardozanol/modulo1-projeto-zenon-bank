package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        this.transactions = transactions.stream().filter(Transaction::isFraud).toList();
    }

    public long getTotalFraud() {
        return transactions.size();
    }

    public List<BigDecimal> getTopHighestAmount() {
        return transactions.stream().sorted(Comparator.reverseOrder()).limit(3).map(Transaction::amount).toList();
    }

    public Stream<String> getNameSuspiciousCustomers() {
        return transactions.stream().sorted(Comparator.reverseOrder()).limit(5).map(transaction -> transaction.origin().name()).distinct();
    }

    public BigDecimal getTotalLoss() {
        return transactions.stream().map(Transaction::amount).reduce(BigDecimal::add).orElse(new BigDecimal("0.0"));
    }

    public Map<TransactionType, Long> getFraudByType() {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

}
