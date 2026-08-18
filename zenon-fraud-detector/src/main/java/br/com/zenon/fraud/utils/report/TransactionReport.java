package br.com.zenon.fraud.utils.report;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record ReportTransaction(BigDecimal amount, boolean isFraud) {
    }

    public Statistics report(String fileName) {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            return lines.skip(1).map(this::parseReportTransaction).filter(Optional::isPresent).map(Optional::get).reduce(
                    new Statistics(0, 0, BigDecimal.ZERO),
                    (Statistics acc, ReportTransaction rt) -> {
                        return new Statistics(acc.totalLines() + 1, acc.totalFraud() + (rt.isFraud() ? 1 : 0), acc.totalAmount().add(rt.amount));
                    }, (s1, s2) -> s1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<ReportTransaction> parseReportTransaction(String line) {
        try {
            String[] fields = line.split(",");

            BigDecimal amount = new BigDecimal(fields[2]);
            boolean isFraud = "1".equals(fields[9]);

            return Optional.of(new ReportTransaction(amount, isFraud));

        } catch (Exception e) {
            System.err.println("Error: " + line + " | " + e);
        }

        return Optional.empty();
    }

}
