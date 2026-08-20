package br.com.zenon.fraud.utils.repository;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Optional;

public class TransactionSQLRespository implements TransactionRepository {

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        return Optional.empty();
    }

    private Optional<Long> getCustomerIdByName(String name) {
        try (Connection connection = SQLConnection.get()) {
            String sqlSelectCustomer = "SELECT (ID) FROM TRANSACTION_CUSTOMERS WHERE NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectCustomer);

            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getLong("ID"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long insertTransactionCustomer(TransactionCustomer customer) {
        try (Connection connection = SQLConnection.get()) {
            String sqlInsertTransactionCustomer = "INSERT INTO TRANSACTION_CUSTOMERS (NAME, OLD_BALANCE, NEW_BALANCE) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTransactionCustomer, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, customer.name());
            preparedStatement.setBigDecimal(2, customer.oldBalance());
            preparedStatement.setBigDecimal(3, customer.newBalance());

            long id = 0;

            if (preparedStatement.executeUpdate() > 1) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                }
            }

            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Transaction transaction) {
        try (Connection connection = SQLConnection.get()) {
            String sqlInsertTransaction = "INSERT INTO TRANSACTIONS (STEP, TYPE, AMOUNT, ORIGIN_ID, RECIPIENT_ID, ISFRAUD, ISFLAGGEDFRAUD) VALUES (?, ?, ?, ?, ?, ?, ?)";

            long idOrigin = getCustomerIdByName(transaction.origin().name())
                    .orElse(insertTransactionCustomer(transaction.origin()));

            long idRecipient = getCustomerIdByName(transaction.recipient().name())
                    .orElse(insertTransactionCustomer(transaction.recipient()));

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTransaction);

            preparedStatement.setLong(1, transaction.step());
            preparedStatement.setString(2, transaction.type().name());
            preparedStatement.setBigDecimal(3, transaction.amount());
            preparedStatement.setLong(4, idOrigin);
            preparedStatement.setLong(5, idRecipient);
            preparedStatement.setBoolean(6, transaction.isFraud());
            preparedStatement.setBoolean(7, transaction.isFlaggedFraud());

            return preparedStatement.executeUpdate() > 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private class SQLConnection {

        private static final String URL = "jdbc:mysql://localhost:3306/zenonDB";
        private static final String USER = "root";
        private static final String PASSWORD = System.getenv("MYSQL_ROOT_PASSWORD");

        public static Connection get() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

    }

}
