package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.report.Statistics;
import br.com.zenon.fraud.utils.report.TransactionReport;

public class TestNIO {

    static void main() {

        TransactionReport transactionReport = new TransactionReport();
        Statistics statistics = transactionReport.report("data/PS_20174392719_1491204439457_log.csv");

        System.out.println("Total de linhas: " + statistics.totalLines());
        System.out.println("Total de Fraudes: " + statistics.totalFraud());
        System.out.println("Valor total transacionado: " + statistics.totalAmount());

    }

}
