package br.com.zenon.fraud.models.report;

import java.math.BigDecimal;

public record Statistics(long totalLines, long totalFraud, BigDecimal totalAmount) {
}
