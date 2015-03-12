package org.openscore.web.client;

import com.google.gson.Gson;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/4/15
 * Time: 10:50 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JsonTest  {

    private static Gson gson = new Gson();

    @Test
    public void jsonTest() {

        ExecutionTriggeringVo triggerVo = new ExecutionTriggeringVo();

        triggerVo.setSlangFilePath("C:\\PROJS\\slang-content\\org\\openscore\\slang\\base\\print\\print_text.sl");

        triggerVo.setSlangDir("C:\\PROJS\\slang-content\\org\\openscore\\slang");

        Map<String, Object> inputs = new HashMap<>();
        inputs.put("text", "blabla");
        triggerVo.setRunInputs(inputs);

        triggerVo.setSystemProperties(null);

        String str = getJsonString(triggerVo);

        System.out.println(str);

        Assert.assertNotNull(str);

        //just making sure the object can be converted to String and from String by json
        ExecutionTriggeringVo executionTriggeringVo = gson.fromJson(str, ExecutionTriggeringVo.class);

        Assert.assertNotNull(executionTriggeringVo);
    }

    public String getJsonString(Object object){
        return gson.toJson(object);
    }

    @Configuration
    static class Configurator {}
}
