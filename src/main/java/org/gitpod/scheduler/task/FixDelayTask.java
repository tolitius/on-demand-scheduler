package org.gitpod.scheduler.task;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;

/**
 * <p> {@link Schedulable} task that upon calling its schedule() method invokes {@link Runnable} at
 *     the specified execution time and subsequently with the given delay between the completion of one execution and
 *     the start of the next.</p>
 *
 * <p> Execution will end once the scheduler shuts down or the returned {@link ScheduledFuture} gets cancelled.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public class FixDelayTask extends AbstractSchedulableTask {

    private Long delay = -1L;
    private Date startTime;

    public FixDelayTask ( Runnable task, Long delay, Date startTime, TaskScheduler scheduler ) {
        super.setTask( task );
        super.setScheduler( scheduler );

        this.delay = delay;
        this.startTime = startTime;        
    }

	public void schedule() {
		if ( startTime == null ) {
			super.setScheduledFuture( getScheduler().scheduleWithFixedDelay( getTask(), delay ) );
		}
		else {
			super.setScheduledFuture( getScheduler().scheduleWithFixedDelay( getTask(), startTime ,delay ) );
		}
	}
	
	// public accessors
    
	public Long getDelay() {
		return delay;
	}
    public Date getStartTime() {
        return startTime;
    }
}
