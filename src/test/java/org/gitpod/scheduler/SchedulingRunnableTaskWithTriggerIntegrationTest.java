package org.gitpod.scheduler;

import org.gitpod.mailer.Mailer;
import org.gitpod.scheduler.trigger.DurationTrigger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:./META-INF/conf/runnable-task-with-trigger-test-conf.xml" } )
public class SchedulingRunnableTaskWithTriggerIntegrationTest {

    @Resource( name="packageDeliveryTask" )
    private Runnable packageDeliveryTask;

    @Resource( name="taskScheduler" )
    private TaskScheduler taskScheduler;

    @Resource( name="mailer" )
    private Mailer mailer;

    @Test
    public void shouldCreateTriggerAndScheduleTask() {

        long now = Calendar.getInstance().getTime().getTime();

        // Set a trigger to start in 5 second, run every 2 seconds for 15 seconds:
        Date startTime =  new Date( now + 5000 );
        Date endTime =  new Date( now + 15000 );
        long period = 2000;

        Trigger trigger = new DurationTrigger( startTime, endTime, period );

        ScheduledFuture task = taskScheduler.schedule( packageDeliveryTask, trigger );

        assertNotNull( "Expected a task to be scheduled, but it was not", task );
    }

    @Test
    public void shouldNotDeliverAnythingYet() throws Exception {
        Thread.sleep( 2000 );
        assertEquals( "Mailman is working too fast today... More packages than needed was delivered",
                      0, mailer.packagesDelivered() );
    }

    @Test
    public void shouldDeliverSomethingAlready() throws Exception {

        Thread.sleep( 10000 );
        assertTrue( "Mailman is pretty slow today... Expected number of packages was not delivered",
                    mailer.packagesDelivered() > 3 );
    }

    @Test
    public void shouldBeDoneWithAllDeliveries() throws Exception {
        Thread.sleep( 5000 );
        assertEquals( "Mailman is out of it today... Expected number of packages was not delivered",
                      6, mailer.packagesDelivered() );
    }

    @Test
    public void shouldNotDeliverAnyMorePackages() throws Exception {
        Thread.sleep( 5000 );
        assertEquals( "Mailman is cheating and delivering packages for other mail companies...",
                      6, mailer.packagesDelivered() );        
    }
}
