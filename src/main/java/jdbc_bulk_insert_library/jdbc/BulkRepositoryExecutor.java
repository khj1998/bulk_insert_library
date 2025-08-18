package jdbc_bulk_insert_library.jdbc;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BulkRepositoryExecutor {
    private final List<BulkRepository<?>> bulkRepositories;

    @Transactional
    public <T> void execute(Class<T> entityType,int batchSize,List<Map<String, Object>> params) {
        int totalSize = params.size();

        if (totalSize <= 0) {
            return;
        }

        BulkRepository<T> targetRepository = findTargetRepository(entityType);

        for (int i = 0; i < totalSize; i += batchSize) {
            int end = Math.min(i + batchSize, totalSize);
            List<Map<String, Object>> batchParams = params.subList(i, end);
            targetRepository.saveAllInBatch(batchParams);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> BulkRepository<T> findTargetRepository(Class<T> entityType) {
        return (BulkRepository<T>) bulkRepositories.stream()
                .filter(bulkRepository -> bulkRepository.getSupportedType().equals(entityType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하는 BulkRepository를 찾을 수 없습니다: " + entityType.getSimpleName()));
    }
}
