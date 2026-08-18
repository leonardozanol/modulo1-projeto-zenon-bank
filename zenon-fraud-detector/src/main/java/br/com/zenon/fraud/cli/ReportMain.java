package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.report.Statistics;
import br.com.zenon.fraud.utils.report.TransactionReport;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportMain {

    private static final Locale en = Locale.of("en", "us");
    private static final Locale pt_BR = Locale.of("pt", "br");

    static void main(String[] args) {

        Locale locale = en;

        Options options = new Options();

        options.addOption("pt_br", "pt_br", false, "Set the application locale to PT_BR");
        options.addOption("en", "en", false, "Set the application locale to EN");

        try {
            CommandLine commandLine = new DefaultParser().parse(options, args);

            if (commandLine.hasOption("pt_br")) {
                locale = pt_BR;
            }

            if (commandLine.hasOption("en")) {
                locale = en;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle("report", locale);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        TransactionReport transactionReport = new TransactionReport();
        Statistics statistics = transactionReport.report("data/PS_20174392719_1491204439457_log.csv");

        System.out.println(resourceBundle.getString("report.main.totalLines") + ": " + statistics.totalLines());
        System.out.println(resourceBundle.getString("report.main.totalFrauds") + ": " + statistics.totalFraud());
        System.out.println(resourceBundle.getString("report.main.totalValue") + ": " + numberFormat.format(statistics.totalAmount()));

    }

}
