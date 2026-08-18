package br.com.zenon.fraud.utils.report;

import java.math.BigDecimal;

public record Statistics(long totalLines, long totalFraud, BigDecimal totalAmount) {
}
