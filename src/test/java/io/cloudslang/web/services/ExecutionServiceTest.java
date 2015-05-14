package io.cloudslang.web.services;

import io.cloudslang.lang.api.Slang;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import io.cloudslang.web.repositories.ExecutionSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 3/11/15
 * Time: 3:53 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ExecutionServiceTest {

    @Autowired
    ExecutionsService service;

    @Test
    public void testGetAllFilesRecursively(){

//        Set<File> set = new HashSet<>();
//
//        Set<File> result = getAllFilesRecursively(new File("C:\\NATASHA_PERSONAL\\books"), set);
//
//        System.out.println(result.size());

    }

    @Configuration
    static class Configurator {

        @Bean
        public ExecutionsService getExecutionsService(){
            return new ExecutionsServiceImpl();
        }

        @Bean
        public ExecutionSummaryRepository getExecutionSummaryRepository(){
            return Mockito.mock(ExecutionSummaryRepository.class);
        }

        @Bean
        public Slang getSlang(){
            return Mockito.mock(Slang.class);
        }
    }
}
