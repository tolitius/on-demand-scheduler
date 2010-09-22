package org.gitpod.scheduler.task;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.gitpod.scheduler.Schedulable;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.MethodInvokingRunnable;

/**
 * <p> {@link org.gitpod.scheduler.Schedulable} task that upon calling its schedule() method invokes {@link Runnable} at
 *     the specified execution time and subsequently with the given delay between the completion of one execution and
 *     the start of the next.</p>
 *
 * <p> Execution will end once the scheduler shuts down or the returned {@link ScheduledFuture} gets cancelled.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public class FixDelayTask implements Schedulable {

    private Long delay = -1L;
    private Runnable task;
    private Date startTime;

    private TaskScheduler scheduler;

	private ScheduledFuture scheduledFuture;

    public FixDelayTask ( Runnable task, Long delay, Date startTime, TaskScheduler scheduler ) {
        this.task = task;
        this.delay = delay;
        this.startTime = startTime;

        // TODO: thinking on refactoring the scheduler out of here... or NOT :)      
        this.scheduler = scheduler;
    }

	public void schedule() {
		if ( startTime == null ) {
			scheduledFuture = scheduler.scheduleWithFixedDelay( task, delay );
		}
		else {
			scheduledFuture = scheduler.scheduleWithFixedDelay( task, startTime, delay );
		}
	}

	public void cancel() {
		if ( scheduledFuture != null ) {
            scheduledFuture.cancel( true );
        }
	}	
	
	// public accessors
    
	public Long getDelay() {
		return delay;
	}
    public Date getStartTime() {
        return startTime;
    }
    public Runnable getTask() {
        return task;
    }
}
