package br.com.zenon.fraud.exceptions;

public class InvalidCsvHeaderException extends RuntimeException {
    public InvalidCsvHeaderException(String message) {
        super(message);
    }
}
