package org.gitpod.scheduler.task;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.gitpod.scheduler.Schedulable;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.MethodInvokingRunnable;

public class FixDelayTask implements Schedulable, InitializingBean, DisposableBean {

	private Long delay = -1L;
	private MethodInvokingRunnable task;
	private Date startTime;
	
	private String methodName;
	private Object targetObject;
	private Object[] arguments;

	private ScheduledFuture scheduledFuture;
	
	public void schedule( TaskScheduler scheduler ) {
		if ( startTime == null ) {
			scheduledFuture = scheduler.scheduleWithFixedDelay( task, delay );
		}
		else {
			scheduledFuture = scheduler.scheduleWithFixedDelay( task, startTime, delay );
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		
		if ( delay < 0 ) {
			throw new IllegalArgumentException( "'delay' must be set and be positive" );
		}
		
		task = new MethodInvokingRunnable();
		task.setTargetObject( targetObject );
		task.setTargetMethod( methodName );
		task.setArguments( arguments );
		
		task.afterPropertiesSet();
	}
	
	public void destroy() {
		if ( scheduledFuture != null ) {
            scheduledFuture.cancel( true );
        }
	}	
	
	// public accessors
	
	public Long getDelay() {
		return delay;
	}

	public Runnable getTask() {
		return task;
	}
	
	public void setDelay( Long delay ) {
		this.delay = delay;
	}

	public void setMethodName( String methodName ) {
		this.methodName = methodName;
	}

	public void setArguments( Object[] arguments ) {
		this.arguments = arguments;
	}
	
	public void setTargetObject( Object targetObject ) {
		this.targetObject = targetObject;
	}
	
	public void setStartTime( Date startTime ) {
		this.startTime = startTime;
	}
}
