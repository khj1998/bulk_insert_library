package jdbc_bulk_insert_library.jdbc.sample_code;

import jdbc_bulk_insert_library.jdbc.BulkRepositoryExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleClient implements CommandLineRunner {
    private final BulkRepositoryExecutor bulkRepositoryExecutor;

    @Override
    public void run(String... args) throws Exception {
        List<Map<String, Object>> paramsList = new ArrayList<>();
        Random random = new Random();
        String[] statuses = {"active", "inactive", "banned"};

        int totalSize = 100000;

        for (int i = 0; i < totalSize; i++) {
            Map<String, Object> params = new HashMap<>();
            params.put("uuid", UUID.randomUUID().toString());
            params.put("status", statuses[random.nextInt(statuses.length)]);

            paramsList.add(params);
        }

        log.info("Starting batch save for {} SampleJdbcEntities...", totalSize);

        bulkRepositoryExecutor.execute(SampleJdbcEntity.class,1000,paramsList);
    }
}
