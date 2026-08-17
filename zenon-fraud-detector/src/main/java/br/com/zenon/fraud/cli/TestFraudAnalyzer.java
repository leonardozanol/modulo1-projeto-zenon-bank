package br.com.zenon.fraud.cli;

import br.com.zenon.fraud.utils.FraudAnalyzer;

public class TestFraudAnalyzer {

    static void main() {

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer("data/PS_20174392719_1491204439457_log.csv");
        fraudAnalyzer.getTotalFraud();

        fraudAnalyzer.getTopHighestAmount();

    }

}
