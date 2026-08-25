package br.com.zenon.fraud.utils.ingestor;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EfficientTransactionIngestor {

    private static int LIMIT_BATCHES_READ = 2_500;
    private static int THREAD_POOL = 10;

    public static void readAsStream(String fileName, Consumer<Transaction> consumer) {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {

            lines.skip(1).map(EfficientTransactionIngestor::parseTransaction).filter(Optional::isPresent).map(Optional::get).forEach(
                    consumer
            );

        } catch (IOException e) {
            throw new RuntimeException("Erro Ao Ler Arquivo " + fileName + ": " + e);
        }
    }

    public static void readAsBatch(String fileName, Consumer<List<Transaction>> consumer) {
        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL); Stream<String> lines = Files.lines(Path.of(fileName)).skip(1)) {

            Iterator<String> iterator = lines.iterator();

            List<String> lineBatch = new ArrayList<>(LIMIT_BATCHES_READ);
            while (iterator.hasNext()) {
                lineBatch.add(iterator.next());

                if (lineBatch.size() > LIMIT_BATCHES_READ) {
                    final List<String> currentLineBatch = List.copyOf(lineBatch);
                    lineBatch.clear();

                    executor.submit(() -> executeBatch(currentLineBatch, consumer));
                }
            }

            if (!lineBatch.isEmpty()) {
                final List<String> currentLineBatch = List.copyOf(lineBatch);
                lineBatch.clear();

                executor.submit(() -> executeBatch(currentLineBatch, consumer));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro Ao Ler Arquivo " + fileName + ": " + e);
        }
    }

    private static void executeBatch(List<String> lineBatch, Consumer<List<Transaction>> consumer) {
        consumer.accept(
                lineBatch
                        .stream()
                        .map(EfficientTransactionIngestor::parseTransaction)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
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
