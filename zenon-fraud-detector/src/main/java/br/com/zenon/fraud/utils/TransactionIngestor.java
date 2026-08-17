package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.exceptions.InvalidCsvHeaderException;
import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class TransactionIngestor {

    private static final Logger logger = Logger.getLogger(TransactionIngestor.class.getName());

    public static List<Transaction> read(String nameFile) {
        List<Transaction> transactions = new ArrayList<>();
        readFile(nameFile, transactions::add);

        return transactions;
    }

    public static Map<String, Transaction> readToMap(String nameFile) {
        Map<String, Transaction> transactions  = new HashMap<>();
        readFile(nameFile, transaction -> transactions.put(transaction.origin().name(), transaction));

        return transactions;
    }

    private static void readFile(String nameFile, Consumer<Transaction> transactionConsumer) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nameFile))) {
            if (!bufferedReader.readLine().equals("step,type,amount,nameOrig,oldbalanceOrg,newbalanceOrig,nameDest,oldbalanceDest,newbalanceDest,isFraud,isFlaggedFraud")) {
                throw new InvalidCsvHeaderException("Header CSV Is Not Valid!");
            }

            int counter = 0;
            String line;

            while (counter < 100000 && (line = bufferedReader.readLine()) != null) {
                parseTransaction(line).ifPresent(transactionConsumer);
                counter++;
            }

        } catch (NoSuchFileException e) {
            logger.warning(() -> "Error No Such File" + nameFile);
            throw new RuntimeException("Error No Such File: " + nameFile);

        } catch (NumberFormatException e) {
            logger.warning("Error In Number Format");
            throw new RuntimeException("Error Number Format");

        } catch (IOException e) {
            logger.warning(() -> "Error In Application");
            throw new RuntimeException(e);
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
