package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.models.Transaction;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TransactionReport {

    private long totalLines = 0;
    private long totalFrauds = 0;
    private BigDecimal totalTraded = BigDecimal.ZERO;

    public void report(String fileName) {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            lines.skip(1).forEach(line -> {
                String[] fields = line.split(",");
                BigDecimal amount = new BigDecimal(fields[2]);
                boolean isFraud = "1".equals(fields[9]);

                if (isFraud) {
                    totalFrauds++;
                }

                totalTraded = totalTraded.add(amount);
                totalLines++;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public long getTotalLines() {
        return totalLines;
    }

    public long getTotalFrauds() {
        return totalFrauds;
    }

    public BigDecimal getTotalTraded() {
        return totalTraded;
    }

}
