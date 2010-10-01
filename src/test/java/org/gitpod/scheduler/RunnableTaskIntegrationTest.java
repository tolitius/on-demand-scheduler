package org.gitpod.scheduler;

import org.gitpod.mailer.Mailer;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:./META-INF/conf/runnable-task-test-conf.xml" } )
public class RunnableTaskIntegrationTest {

    @Resource( name="packageDeliveryTask" )
    private Runnable packageDeliveryTask;

    @Resource( name="taskScheduler" )
    private TaskScheduler taskScheduler;

    @Resource( name="mailer" )
    private Mailer mailer;    

    @Test
    public void shouldScheduleDeliveryTask() throws Exception {

        Calendar cal = Calendar.getInstance();
        cal.add( cal.SECOND, 5 );
        
        taskScheduler.schedule( packageDeliveryTask, cal.getTime() );

        Thread.sleep( 2000 );
        assertEquals( "Mailman is working too fast today... More packages than needed was delivered",
                      0, mailer.packagesDelivered() );

        Thread.sleep( 4000 );
        assertEquals( "Mailman is pretty slow today... Expected number of mails was not delivered",
                      1, mailer.packagesDelivered() );

        // shutting down the scheduler
        ( (ThreadPoolTaskScheduler) taskScheduler ).shutdown();
    }
}
