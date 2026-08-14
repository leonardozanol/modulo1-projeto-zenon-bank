package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionType;

import java.math.BigDecimal;

public class TestTransaction {

    static void main() {

        Transaction transaction1 = new Transaction(
                1L,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                new TransactionCustomer("C1231006815", new BigDecimal("170130.0"), new BigDecimal("160296.36")),
                new TransactionCustomer("M1979787155", new BigDecimal("0.0"), new BigDecimal("0.0")),
                false,
                false
        );

        Transaction transaction2 = new Transaction(
                743L,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                new TransactionCustomer("C1280323807", new BigDecimal("850002.52"), new BigDecimal("0.0")),
                new TransactionCustomer("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63")),
                true,
                false
        );

        IO.println("Transação 1: " + transaction1);
        IO.println("Transação 2: " + transaction2);

    }

}
