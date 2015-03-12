package org.openscore.web.services;

import org.openscore.events.ScoreEvent;
import org.openscore.events.ScoreEventListener;
import org.openscore.facade.execution.ExecutionStatus;
import org.openscore.lang.api.Slang;
import org.openscore.lang.entities.ScoreLangConstants;
import org.openscore.lang.runtime.events.LanguageEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/4/15
 * Time: 1:34 PM
 */

@Component
public class ExecutionEventsListener4Web implements ScoreEventListener {

    @Autowired
    Slang slang;

    @Autowired
    ExecutionsService service;

    @PostConstruct
    public void init() {
        Set<String> eventTypes = new HashSet<>();

        eventTypes.add(ScoreLangConstants.SLANG_EXECUTION_EXCEPTION); //Runtime exception – flow finished in the middle
        eventTypes.add(ScoreLangConstants.EVENT_EXECUTION_FINISHED); //Flow completed

        slang.subscribeOnEvents(this, eventTypes);
    }

    @Override
    public void onEvent(ScoreEvent event) throws InterruptedException {

        @SuppressWarnings("unchecked")
        Map<String, Serializable> data = (Map<String, Serializable>) event.getData();

        Long executionId = (Long) data.get(LanguageEventData.EXECUTIONID);

        if (event.getEventType().equals(ScoreLangConstants.SLANG_EXECUTION_EXCEPTION)) {

            String exception = (String) data.get(LanguageEventData.EXCEPTION);
            service.updateExecution(executionId, ExecutionStatus.SYSTEM_FAILURE, exception, null);

        }
        else if (event.getEventType().equals(ScoreLangConstants.EVENT_EXECUTION_FINISHED)) {

            String result = (String) data.get(LanguageEventData.RESULT);
            String outputs = data.get(LanguageEventData.OUTPUTS).toString();

            service.updateExecution(executionId, ExecutionStatus.COMPLETED, result, outputs);
        }
    }
}
