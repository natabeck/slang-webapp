package io.cloudslang.web.services;

import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/8/15
 * Time: 3:11 PM
 */
public interface ExecutionsService {

    /**
     * Trigger flow written in slang
     *
     * @param executionTriggeringVo the value object containing the necessary information to run the flow
     * @return the execution ID in score
     */
    @Transactional
    Long triggerExecution(ExecutionTriggeringVo executionTriggeringVo);

    @Transactional(readOnly = true)
    ExecutionSummaryWebVo getExecution(Long executionId);

    @Transactional
    void updateExecution(Long executionId, ExecutionStatus status, String result, String outputs);
}
