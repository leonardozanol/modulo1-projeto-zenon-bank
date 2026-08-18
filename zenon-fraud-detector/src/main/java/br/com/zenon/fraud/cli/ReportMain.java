package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.report.Statistics;
import br.com.zenon.fraud.utils.report.TransactionReport;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    static void main(String[] args) {

        Locale locale = setLocale(args);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("report", locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        NumberFormat integerFormat = NumberFormat.getInstance(locale);

        TransactionReport transactionReport = new TransactionReport();
        Statistics statistics = transactionReport.report("data/PS_20174392719_1491204439457_log.csv");

        String labelTotalLines = resourceBundle.getString("report.main.totalLines");
        String labelTotalFrauds = resourceBundle.getString("report.main.totalFrauds");
        String labelTotalValue = resourceBundle.getString("report.main.totalValue");

        String formattedTotalLines = integerFormat.format(statistics.totalLines());
        String formattedTotalFrauds = integerFormat.format(statistics.totalFraud());
        String formattedTotalValue = currencyFormat.format(statistics.totalAmount());

        System.out.printf("%s: %s%n%s: %s%n%s: %s%n", labelTotalLines, formattedTotalLines, labelTotalFrauds, formattedTotalFrauds, labelTotalValue, formattedTotalValue);

    }

    private static Locale setLocale(String[] args) {
        if (args.length > 0 && (args[0].equals("--pt_br") || args[0].equals("-pt_br"))) {
            return Locale.of("pt", "br");
        }

        return Locale.of("en", "us");
    }

}
