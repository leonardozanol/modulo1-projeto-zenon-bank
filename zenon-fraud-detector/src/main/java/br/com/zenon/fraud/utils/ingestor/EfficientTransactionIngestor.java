package br.com.zenon.fraud.utils.ingestor;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {

    private static int LIMIT_READ = 10_000;

    public static void readAsStream(String fileName, Consumer<Transaction> consumer) {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {

            lines.skip(1).limit(LIMIT_READ).map(EfficientTransactionIngestor::parseTransaction).filter(Optional::isPresent).map(Optional::get).forEach(
                    consumer
            );

        } catch (IOException e) {
            throw new RuntimeException("Erro Ao Ler Arquivo " + fileName + ": " + e);
        }
    }

    private static Optional<Transaction> parseTransaction(String line) {
        try {
            String[] fields = line.split(",");

            Long step = Long.parseLong(fields[0]);
            TransactionType type = TransactionType.valueOf(fields[1]);
            BigDecimal amount = new BigDecimal(fields[2]);
            TransactionCustomer origin = new TransactionCustomer(fields[3], new BigDecimal(fields[4]), new BigDecimal(fields[5]));
            TransactionCustomer recipient = new TransactionCustomer(fields[6], new BigDecimal(fields[7]), new BigDecimal(fields[8]));
            boolean isFraud = "1".equals(fields[9]);
            boolean isFlaggedFraud = "1".equals(fields[10]);

            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));

        } catch (Exception e) {
            System.err.println("Error: " + line + " | " + e);
        }

        return Optional.empty();

    }

}
