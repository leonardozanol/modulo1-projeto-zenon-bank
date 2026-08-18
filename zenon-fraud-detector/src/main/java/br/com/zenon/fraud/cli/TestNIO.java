package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.TransactionReport;

public class TestNIO {

    static void main() {

        TransactionReport transactionReport = new TransactionReport();
        transactionReport.report("data/PS_20174392719_1491204439457_log.csv");

        System.out.println("Total de linhas: " + transactionReport.getTotalLines());
        System.out.println("Total de Fraudes: " + transactionReport.getTotalFrauds());
        System.out.println("Valor total transacionado: " + transactionReport.getTotalTraded());

    }

}
