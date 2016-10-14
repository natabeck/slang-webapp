package io.cloudslang.web.services;

import io.cloudslang.lang.api.Slang;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.lang.entities.SystemProperty;
import io.cloudslang.lang.entities.bindings.values.Value;
import io.cloudslang.lang.entities.bindings.values.ValueFactory;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.client.ExecutionSummaryWebVo;
import io.cloudslang.web.client.ExecutionTriggeringVo;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.repositories.ExecutionSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 10:44 AM
 */

@Service
public class ExecutionsServiceImpl implements ExecutionsService {

    @Autowired
    Slang slang;

    @Autowired
    ExecutionSummaryRepository repository;

    @Override
    @Transactional
    public Long triggerExecution(ExecutionTriggeringVo executionTriggeringVo) {
        SlangSource flowSource = SlangSource.fromFile(new File(executionTriggeringVo.getSlangFilePath()));
        String slangDir = executionTriggeringVo.getSlangDir();
        Map<String, Value> inputs = getInputs(executionTriggeringVo);
        Set<SystemProperty> systemProperties = getSystemProperties(executionTriggeringVo);


        Long executionId = slang.compileAndRun(flowSource, getDependencies(slangDir), inputs, systemProperties);

        ExecutionSummaryEntity execution = new ExecutionSummaryEntity();
        execution.setExecutionId(executionId);
        execution.setStatus(ExecutionStatus.RUNNING);

        repository.save(execution);

        return execution.getExecutionId();
    }

    private Map<String, Value> getInputs(ExecutionTriggeringVo executionTriggeringVo) {
        Map<String, Value> inputs = new HashMap<>();
        if(executionTriggeringVo.getRunInputs() != null){
            for(Map.Entry<String, Object> entry : executionTriggeringVo.getRunInputs().entrySet()){
                inputs.put(entry.getKey(), ValueFactory.create((Serializable) entry.getValue()));
            }
        }
        return inputs;
    }

    public Set<SystemProperty> getSystemProperties(ExecutionTriggeringVo executionTriggeringVo) {
        Set<SystemProperty> systemProperties = new HashSet<>();
        if(executionTriggeringVo.getSystemProperties() != null){
            for(Map.Entry<String, String> entry : executionTriggeringVo.getSystemProperties().entrySet()){
                systemProperties.add(new SystemProperty(entry.getKey(), entry.getValue()));
            }
        }
        return systemProperties;
    }

    @Override
    @Transactional(readOnly = true)
    public ExecutionSummaryWebVo getExecution(Long executionId){
        ExecutionSummaryEntity execution = repository.findByExecutionId(executionId);
        if (execution != null) {
            return new ExecutionSummaryWebVo(
                    execution.getExecutionId(),
                    execution.getStatus().name(),
                    execution.getResult(),
                    execution.getOutputs());
        }
        return null;
    }

    @Override
    @Transactional
    public void updateExecution(Long executionId, ExecutionStatus status, String result, String outputs){
        ExecutionSummaryEntity execution = repository.findByExecutionId(executionId);
        execution.setStatus(status);
        execution.setResult(result);
        execution.setOutputs(outputs);
        repository.save(execution);
    }

    private Set<SlangSource> getDependencies(String slangDir){

        Set<SlangSource> slangDependencies = new HashSet<>();

        File dir = new File(slangDir);

        Set<File> files = getAllFilesRecursively(dir, new HashSet<File>());

        for(File file : files){
            slangDependencies.add(SlangSource.fromFile(file));
        }

        return slangDependencies;
    }

    private static Set<File> getAllFilesRecursively(File directory, Set<File> result) {
        File[] filesInDir = directory.listFiles();
        //If it is a file (filesInDir == null in case the directory is a file) - add it to list and return
        if(filesInDir == null){
            result.add(directory);
            return result;
        }
        //If it is a directory - do recursive call for each child
        else {
            for (File file : filesInDir){
                result.addAll(getAllFilesRecursively(file, result));
            }
            return result;
        }
    }
}
