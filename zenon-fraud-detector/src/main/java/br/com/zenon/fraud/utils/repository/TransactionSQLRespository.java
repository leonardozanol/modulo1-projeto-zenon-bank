package br.com.zenon.fraud.utils.repository;

import br.com.zenon.fraud.models.Transaction;
import br.com.zenon.fraud.models.TransactionCustomer;
import br.com.zenon.fraud.models.TransactionType;
import com.mysql.cj.jdbc.Driver;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class TransactionSQLRespository implements TransactionRepository {

    @Override
    public Optional<Transaction> getByNameCustomer(String nameCustomer) {
        try (Connection connection = SQLConnection.get()) {
            String sqlSelectCustomer = "SELECT STEP, TYPE, AMOUNT, NAME_ORIGIN, OLD_BALANCE_ORIGIN, NEW_BALANCE_ORIGIN, NAME_RECIPIENT, OLD_BALANCE_RECIPIENT, NEW_BALANCE_RECIPIENT, ISFRAUD, ISFLAGGEDFRAUD FROM TRANSACTIONS WHERE NAME_ORIGIN = ? LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectCustomer);
            preparedStatement.setString(1, nameCustomer);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return Optional.of(new Transaction(
                        rs.getLong("STEP"),
                        TransactionType.valueOf(rs.getString("TYPE")),
                        rs.getBigDecimal("AMOUNT"),
                        new TransactionCustomer(
                                rs.getString("NAME_ORIGIN"),
                                rs.getBigDecimal("OLD_BALANCE_ORIGIN"),
                                rs.getBigDecimal("NEW_BALANCE_ORIGIN")
                        ),
                        new TransactionCustomer(
                                rs.getString("NAME_RECIPIENT"),
                                rs.getBigDecimal("OLD_BALANCE_RECIPIENT"),
                                rs.getBigDecimal("NEW_BALANCE_RECIPIENT")
                        ),
                        rs.getBoolean("ISFRAUD"),
                        rs.getBoolean("ISFLAGGEDFRAUD")
                ));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Transaction transaction) {
        try (Connection connection = SQLConnection.get()) {
            String sqlInsertTransaction = "INSERT INTO TRANSACTIONS (STEP, TYPE, AMOUNT, NAME_ORIGIN, OLD_BALANCE_ORIGIN, NEW_BALANCE_ORIGIN, NAME_RECIPIENT, OLD_BALANCE_RECIPIENT, NEW_BALANCE_RECIPIENT, ISFRAUD, ISFLAGGEDFRAUD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTransaction);
            preparedStatement.setLong(1, transaction.step());
            preparedStatement.setString(2, transaction.type().name());
            preparedStatement.setBigDecimal(3, transaction.amount());
            preparedStatement.setString(4, transaction.origin().name());
            preparedStatement.setBigDecimal(5, transaction.origin().oldBalance());
            preparedStatement.setBigDecimal(6, transaction.origin().newBalance());
            preparedStatement.setString(7, transaction.recipient().name());
            preparedStatement.setBigDecimal(8, transaction.recipient().oldBalance());
            preparedStatement.setBigDecimal(9, transaction.recipient().newBalance());
            preparedStatement.setBoolean(10, transaction.isFraud());
            preparedStatement.setBoolean(11, transaction.isFlaggedFraud());

            return preparedStatement.executeUpdate() == 1;

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
