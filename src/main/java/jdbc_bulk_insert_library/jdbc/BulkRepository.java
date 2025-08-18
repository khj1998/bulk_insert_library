package jdbc_bulk_insert_library.jdbc;

import java.util.Map;

public interface BulkRepository<T> {
    Class<T> getSupportedType();
    void saveAllInBatch(int batchSize,Map<String,Object> params);
}
