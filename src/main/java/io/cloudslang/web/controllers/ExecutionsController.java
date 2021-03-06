package io.cloudslang.web.controllers;

import com.google.gson.Gson;
import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.services.ExecutionsService;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:12 AM
 */

@RestController
@EnableAutoConfiguration
public class ExecutionsController {

    @Autowired
    ExecutionsService service;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/executions", method = RequestMethod.POST)
    public Long triggerExecution(@RequestBody String executionTriggeringVoStr) {

        ExecutionTriggeringVo executionTriggeringVo = gson.fromJson(executionTriggeringVoStr, ExecutionTriggeringVo.class);

        String slangDir = executionTriggeringVo.getSlangDir();

        Map<String, Serializable> inputs = new HashMap<>();
        if(executionTriggeringVo.getRunInputs() != null){
            for(String key : executionTriggeringVo.getRunInputs().keySet()){
                inputs.put(key, (Serializable) executionTriggeringVo.getRunInputs().get(key));
            }
        }
        Map<String, Serializable> systemProperties = new HashMap<>();
        if(executionTriggeringVo.getSystemProperties()!= null){
            for(String key : executionTriggeringVo.getSystemProperties().keySet()){
                inputs.put(key, (Serializable) executionTriggeringVo.getSystemProperties().get(key));
            }
        }
        return service.triggerExecution(executionTriggeringVo.getSlangFilePath(),
                slangDir,
                inputs,
                systemProperties);
    }

    @RequestMapping(value = "/executions/{executionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExecutionSummaryWebVo> getExecution(@PathVariable("executionId") Long executionId) {
        try {
            ExecutionSummaryEntity execution = service.getExecution(executionId);

            ExecutionSummaryWebVo executionVo = new ExecutionSummaryWebVo(
                    execution.getExecutionId(),
                    execution.getStatus().name(),
                    execution.getResult(),
                    execution.getOutputs());

            //noinspection ConstantConditions
            if (execution != null) {
                return new ResponseEntity<>(executionVo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
