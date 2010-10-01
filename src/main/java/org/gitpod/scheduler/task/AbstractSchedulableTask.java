package org.gitpod.scheduler.task;

import org.springframework.scheduling.TaskScheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * <p> {@link Schedulable} task encapsulates {@link org.springframework.scheduling.TaskScheduler}
 *     and a Runnable task.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public abstract class AbstractSchedulableTask implements Schedulable {

    private Runnable task;

    private TaskScheduler scheduler;
	private ScheduledFuture scheduledFuture;

	public void cancel() {
		if ( scheduledFuture != null ) {
            scheduledFuture.cancel( true );
        }
	}	

    public boolean isScheduled() {
        
        if ( scheduledFuture != null ) {
            return ! ( scheduledFuture.isCancelled() || scheduledFuture.isDone() );
        }
        return false;
    }

    protected void setScheduledFuture( ScheduledFuture scheduledFuture ) {
        this.scheduledFuture = scheduledFuture;
    }
    protected void setScheduler( TaskScheduler scheduler ) {
        this.scheduler = scheduler;
    }
    protected void setTask( Runnable task ) {
        this.task = task;
    }
    protected TaskScheduler getScheduler() {
        return scheduler;
    } 

	// public accessors
    public Runnable getTask() {
        return task;
    }
}
