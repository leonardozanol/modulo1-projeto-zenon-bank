package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.exceptions.InvalidCsvHeaderException;
import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TransactionIngestor {

    private static final Logger logger = Logger.getLogger(Transaction.class.getName());

    public static List<Transaction> read(String file) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (!bufferedReader.readLine().equals("step,type,amount,nameOrig,oldbalanceOrg,newbalanceOrig,nameDest,oldbalanceDest,newbalanceDest,isFraud,isFlaggedFraud")) {
                logger.warning("Error CSV HEADER is not valid to this operation");
                throw new InvalidCsvHeaderException("Header CSV Is Not Valid!");
            }

            int counter = 0;
            String line;

            while (counter < 1000 && (line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                logger.finer(() -> "Fields Processed: " + Arrays.toString(fields));

                Long step = Long.parseLong(fields[0]);
                TransactionType type = TransactionType.valueOf(fields[1]);
                BigDecimal amount = new BigDecimal(fields[2]);
                TransactionCustomer origin = new TransactionCustomer(fields[3], new BigDecimal(fields[4]), new BigDecimal(fields[5]));
                TransactionCustomer recipient = new TransactionCustomer(fields[6], new BigDecimal(fields[7]), new BigDecimal(fields[8]));
                boolean isFraud = Boolean.parseBoolean(fields[9]);
                boolean isFlaggedFraud = Boolean.parseBoolean(fields[10]);

                transactions.add(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));
                counter++;
            }

            return transactions;

        } catch (NoSuchFileException e) {
            logger.warning(() -> "Error No Such File" + file);
            throw new RuntimeException("Error No Such File: " + file);

        } catch (IOException e) {
            logger.warning(() -> "Error In Application");
            throw new RuntimeException(e);

        }

    }
}
