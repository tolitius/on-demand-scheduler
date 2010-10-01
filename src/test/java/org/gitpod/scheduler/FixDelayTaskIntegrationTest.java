package org.gitpod.scheduler;

import org.gitpod.mailer.Mailer;
import org.gitpod.scheduler.task.Schedulable;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = { "classpath:./META-INF/conf/fix-delay-task-test-conf.xml" } )
public class FixDelayTaskIntegrationTest {

    @Resource( name="deliveryTask" )
    private Schedulable deliveryTask;

    @Resource( name="packageDeliveryTask" )
    private Schedulable packageDeliveryTask;

    @Resource( name="mailer" )
    private Mailer mailer;
    
    @Test
    public void shouldScheduleDeliveryTask() {
        
        assertTrue( "task was already scheduled", ! deliveryTask.isScheduled() );
        deliveryTask.schedule();
        assertTrue( "task was not scheduled as expected", deliveryTask.isScheduled() );
    }
            
    @Test
    public void shouldSchedulePackageDeliveryTask() {

        assertTrue( "task was already scheduled", ! packageDeliveryTask.isScheduled() );
        packageDeliveryTask.schedule();
        assertTrue( "task was not scheduled as expected", packageDeliveryTask.isScheduled() );
    }

    @Test
    public void shouldGiveTimeForTasksToFireAndThenCheckIfTheyDid() throws Exception {

        assertTrue( "Mailman is too fast today...",
                    mailer.mailsDelivered() < 4 );
        assertTrue( "Mail truck is too fast today...",
                    mailer.packagesDelivered() < 4 );

        Thread.sleep( 5225 );

        assertTrue( "Mailman is pretty slow today... Expected number of mails was not delivered",
                    mailer.mailsDelivered() > 4 );
        assertTrue( "Mail truck is pretty slow today... Expected number of packages was not delivered",
                    mailer.packagesDelivered() > 4 );

    }

    @Test
    public void cancelTasksAndCheckIfTheyAreCanceled() {
        
        deliveryTask.cancel();
        packageDeliveryTask.cancel();
        
        assertTrue( "task was not cancelled", ! deliveryTask.isScheduled() );
        assertTrue( "task was not cancelled", ! packageDeliveryTask.isScheduled() );
    }
}
