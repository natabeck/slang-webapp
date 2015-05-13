package io.cloudslang.web.services;

import io.cloudslang.lang.api.Slang;
import io.cloudslang.lang.compiler.SlangSource;
import io.cloudslang.score.facade.execution.ExecutionStatus;
import io.cloudslang.web.entities.ExecutionSummaryEntity;
import io.cloudslang.web.repositories.ExecutionSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.Serializable;
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

    /**
     * Trigger flow written in slang
     *
     * @param slangFilePath    the slang file path containing the flow or operation
     * @param slangDir         the parent directory of slang that contains all dependencies
     * @param runInputs        the inputs for the flow or operation run
     * @param systemProperties the system properties for the flow or operation run
     * @return the execution ID in score
     */
    @Override
    @Transactional
    public Long triggerExecution(String slangFilePath,
                                 String slangDir,
                                 Map<String, ? extends Serializable> runInputs,
                                 Map<String, ? extends Serializable> systemProperties) {

        SlangSource flowSource = SlangSource.fromFile(new File(slangFilePath));

        Long executionId = slang.compileAndRun(flowSource, getDependencies(slangDir), runInputs, systemProperties);

        ExecutionSummaryEntity execution = new ExecutionSummaryEntity();
        execution.setExecutionId(executionId);
        execution.setStatus(ExecutionStatus.RUNNING);

        repository.save(execution);

        return execution.getExecutionId();
    }

    @Override
    @Transactional(readOnly = true)
    public ExecutionSummaryEntity getExecution(Long executionId){
        return repository.findByExecutionId(executionId);
    }

    @Override
    @Transactional
    public void updateExecution(Long executionId, ExecutionStatus status, String result, String outputs){
        ExecutionSummaryEntity execution = repository.findByExecutionId(executionId);
        execution.setStatus(status);
        execution.setResult(result);
        execution.setOutputs(outputs);
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
