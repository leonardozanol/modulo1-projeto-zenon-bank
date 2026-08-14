package br.com.zenon.fraud.utils;

import br.com.zenon.fraud.exceptions.InvalidCsvHeaderException;
import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {

    public static List<Transaction> processFile(File file) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (!bufferedReader.readLine().equals("step,type,amount,nameOrig,oldbalanceOrg,newbalanceOrig,nameDest,oldbalanceDest,newbalanceDest,isFraud,isFlaggedFraud")) {
                throw new InvalidCsvHeaderException("Header CSV Is Not Valid!");
            }


            int counter = 0;
            String line;

            while (counter < 1000 && (line = bufferedReader.readLine()) != null) {
                String[] campos = line.split(",");

                Long step = Long.parseLong(campos[0]);
                TransactionType type = TransactionType.valueOf(campos[1]);
                BigDecimal amount = new BigDecimal(campos[2]);
                TransactionCustomer origin = new TransactionCustomer(campos[3], new BigDecimal(campos[4]), new BigDecimal(campos[5]));
                TransactionCustomer recipient = new TransactionCustomer(campos[6], new BigDecimal(campos[7]), new BigDecimal(campos[8]));
                boolean isFraud = Boolean.parseBoolean(campos[9]);
                boolean isFlaggedFraud = Boolean.parseBoolean(campos[10]);

                transactions.add(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));

                counter++;
            }

            return transactions;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
