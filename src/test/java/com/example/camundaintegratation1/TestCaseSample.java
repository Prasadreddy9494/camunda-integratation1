package com.example.camundaintegratation1;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCaseSample {

    private static final String PROCESS_KEY="unitTestid";
@Rule
    public ProcessEngineRule rule=new ProcessEngineRule();

    @Test
    @Deployment(resources = {"unitTestid.bpmn"})
    public void testSampleCase_happyPath(){

        ProcessInstance instance= runtimeService().startProcessInstanceByKey(PROCESS_KEY);

                assertThat(instance)
                        .isActive()
                        .hasPassed("StartEventid")
                        .isWaitingAtExactly("userTask1id")
                        .task().isNotAssigned();

                complete(task(),withVariables("attribute1","prasad","assignPerson","value1"));

                assertThat(instance)
                        .hasPassed("userTask1id")
                        .hasPassedInOrder("userTask1id","service1id")
                        .isWaitingAt("user2id")
                        .task().isAssignedTo("prasad");

                complete(task(),withVariables("attribute2","variableService"));

                assertThat(instance)
                        .hasPassedInOrder("user2id","Endeventid")
                        .isEnded();

    }
}
