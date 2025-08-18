package jdbc_bulk_insert_library.jdbc;

import java.util.List;
import java.util.Map;

public interface BulkRepository<T> {
    Class<T> getSupportedType();
    void saveAllInBatch(List<Map<String, Object>> batchParams);
}
