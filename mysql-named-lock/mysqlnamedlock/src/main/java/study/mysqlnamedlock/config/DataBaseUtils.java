package study.mysqlnamedlock.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBaseUtils {
    private final DataSource dataSource;
    public Connection connection() {
        try {
            return dataSource.getConnection();
        }catch (SQLException e) {
            throw new IllegalArgumentException("연결 실패", e);
        }
    }

}
