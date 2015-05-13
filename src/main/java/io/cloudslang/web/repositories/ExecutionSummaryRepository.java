package io.cloudslang.web.repositories;

import io.cloudslang.web.entities.ExecutionSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/2/15
 * Time: 2:51 PM
 */
public interface ExecutionSummaryRepository extends JpaRepository<ExecutionSummaryEntity, Long>{

    public ExecutionSummaryEntity findByExecutionId(Long executionId);
}