package org.gitpod.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:./META-INF/conf/fix-delay-task-test-conf.xml" } )
public class FixDelayTaskIntegrationTest {

    public static long mailCount = 0;    
    public static long packageCount = 0;

    @Resource( name="deliveryTask" )
    Schedulable deliveryTask;

    @Resource( name="packageDeliveryTask" )
    Schedulable packageDeliveryTask;

    @Resource( name="taskScheduler" )
    TaskScheduler taskScheduler;

    @Test
    public void shouldScheduleDeliveryTask() {
          deliveryTask.schedule( taskScheduler );
    }
            
    @Test
    public void shouldSchedulePackageDeliveryTask() {
          packageDeliveryTask.schedule( taskScheduler );
    }

    @Test
    public void shouldGiveTimeForTasksToFireAndThenCheckIfTheyDid() throws Exception {

        Thread.sleep( 5225 );
        assertTrue( "Mailman is pretty slow today... Expected number of mails was not delivered", mailCount > 4 );
        assertTrue( "Mail truck is pretty slow today... Expected number of packages was not delivered", packageCount > 4 );
    }
}
