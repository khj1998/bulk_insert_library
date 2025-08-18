package jdbc_bulk_insert_library.jdbc;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractJdbcBulkRepository<T> implements BulkRepository<T> {

    private final JdbcTemplate jdbcTemplate;

    public AbstractJdbcBulkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public final void saveAllInBatch(List<Map<String, Object>> batchParams) {
        String sql = getInsertSql();

        this.jdbcTemplate.batchUpdate(sql,
                batchParams,
                batchParams.size(),
                (ps, params) -> setParameters(ps, params));
    }

    protected abstract String getInsertSql();

    protected abstract void setParameters(PreparedStatement ps, Map<String, Object> params) throws SQLException;
}
