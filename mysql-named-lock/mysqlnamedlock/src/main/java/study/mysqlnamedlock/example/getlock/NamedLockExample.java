package study.mysqlnamedlock.example.getlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.mysqlnamedlock.example.getlock.enums.NamedLock;

@Component
@RequiredArgsConstructor
@Slf4j
public class NamedLockExample {

    private static final String GET_LOCK = "SELECT GET_LOCK(?,?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";

    private final DataSource dataSource;

    public <T> T execute(String lockName, long timeout, Supplier<T> supplier) {
        try (Connection connection = dataSource.getConnection()) {
            try {
            Integer lock = getLock(connection, lockName, timeout);
            if(lock == 1) {
                return supplier.get();
            }
            return null;
            } finally {
                release(connection, lockName);
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getLock(Connection connection, String lockName, long timeout) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, lockName);
            preparedStatement.setLong(2, timeout);
            return getLockResult(preparedStatement, NamedLock.GET_LOCK);
        }
    }

    public Integer release(Connection connection, String lockName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, lockName);
            return getLockResult(preparedStatement, NamedLock.RELEASE_LOCK);
        }
    }

    public Integer getLockResult(PreparedStatement preparedStatement, NamedLock namedLock)
        throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                throw new RuntimeException("결과가 존재하지 않음");
            }
            Integer result = resultSet.getInt(1);
            System.out.println(
                Thread.currentThread().getName() + "- 결과 : " + namedLock.name() + " "
                    + namedLock.getResult(result));
            return result;
        }
    }
}
