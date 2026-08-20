package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.TransactionIngestor;
import br.com.zenon.fraud.utils.repository.TransactionSQLRespository;

public class DBMain {

    static void main() {

        TransactionSQLRespository respository = new TransactionSQLRespository();

        TransactionIngestor.read("data/PS_20174392719_1491204439457_log.csv").forEach(respository::save);

    }

}
