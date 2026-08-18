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

        System.out.println(resourceBundle.getString("report.main.totalLines") + ": " + integerFormat.format(statistics.totalLines()));
        System.out.println(resourceBundle.getString("report.main.totalFrauds") + ": " + integerFormat.format(statistics.totalFraud()));
        System.out.println(resourceBundle.getString("report.main.totalValue") + ": " + currencyFormat.format(statistics.totalAmount()));

    }

    private static Locale setLocale(String[] args) {
        if (args.length > 0 && args[0].equals("--pt_br")) {
            return Locale.of("pt", "br");
        }

        return Locale.of("en", "us");
    }

}
