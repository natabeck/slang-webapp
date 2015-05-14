package io.cloudslang.web.client;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/1/15
 * Time: 11:26 AM
 */
public class ExecutionTriggeringVo {

    private String slangFilePath; // the slang file of the flow we want to trigger, location on the server of slangWebApp
    private String slangDir; //The directory where all files are that we need as dependencies
    private Map<String, Object> runInputs;
    private Map<String, Object> systemProperties;


    public String getSlangFilePath() {
        return slangFilePath;
    }

    public void setSlangFilePath(String slangFilePath) {
        this.slangFilePath = slangFilePath;
    }

    public String getSlangDir() {
        return slangDir;
    }

    public void setSlangDir(String slangDir) {
        this.slangDir = slangDir;
    }

    public Map<String, Object> getRunInputs() {
        return runInputs;
    }

    public void setRunInputs(Map<String, Object> runInputs) {
        this.runInputs = runInputs;
    }

    public Map<String, Object> getSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(Map<String, Object> systemProperties) {
        this.systemProperties = systemProperties;
    }
}
