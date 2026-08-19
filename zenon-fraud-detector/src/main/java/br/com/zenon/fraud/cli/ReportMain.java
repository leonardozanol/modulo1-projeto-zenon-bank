package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.report.Statistics;
import br.com.zenon.fraud.utils.report.TransactionReport;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    static void main(String[] args) {

        Locale locale = setLocale(args);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("report", locale);
        NumberFormat integerFormatter = NumberFormat.getInstance(locale);

        NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
        currencyFormatter.setCurrency(Currency.getInstance("USD"));

        TransactionReport transactionReport = new TransactionReport();
        Statistics statistics = transactionReport.report("data/PS_20174392719_1491204439457_log.csv");

        String labelTotalLines = resourceBundle.getString("report.main.totalLines");
        String labelTotalFrauds = resourceBundle.getString("report.main.totalFrauds");
        String labelTotalAmount = resourceBundle.getString("report.main.totalValue");

        String formattedTotalLines = integerFormatter.format(statistics.totalLines());
        String formattedTotalFrauds = integerFormatter.format(statistics.totalFraud());
        String formattedTotalAmount = currencyFormatter.format(statistics.totalAmount());

        System.out.printf("%s: %s%n%s: %s%n%s: %s%n", labelTotalLines, formattedTotalLines, labelTotalFrauds, formattedTotalFrauds, labelTotalAmount, formattedTotalAmount);

    }

    private static Locale setLocale(String[] args) {
        if (args.length > 0 && (args[0].equals("--pt_br") || args[0].equals("-pt_br"))) {
            return Locale.of("pt", "br");
        }

        return Locale.of("en", "us");
    }

}
