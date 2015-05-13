package io.cloudslang.web.services;

import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/8/15
 * Time: 3:11 PM
 */
public interface ExecutionsService {
    @Transactional
    Long triggerExecution(String slangFilePath,
                          String slangDir,
                          Map<String, ? extends Serializable> runInputs,
                          Map<String, ? extends Serializable> systemProperties);

    @Transactional(readOnly = true)
    ExecutionSummaryEntity getExecution(Long executionId);

    @Transactional
    void updateExecution(Long executionId, ExecutionStatus status, String result, String outputs);
}
