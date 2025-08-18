package jdbc_bulk_insert_library.jdbc.sample_code;

import jdbc_bulk_insert_library.jdbc.AbstractJdbcBulkRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Component
public class SampleBulkStatementMapper extends AbstractJdbcBulkRepository<SampleJdbcEntity> {
    public SampleBulkStatementMapper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Class<SampleJdbcEntity> getSupportedType() {
        return SampleJdbcEntity.class;
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO sample_jdbc_table (uuid,status) "+
                "VALUES (?,?)";
    }

    @Override
    protected void setParameters(PreparedStatement ps, Map<String, Object> params) throws SQLException {
        ps.setString(1, (String)params.get("uuid"));
        ps.setString(2, (String)params.get("status"));
    }
}
