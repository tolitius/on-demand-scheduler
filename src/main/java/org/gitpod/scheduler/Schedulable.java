package org.gitpod.scheduler;

import org.springframework.scheduling.TaskScheduler;

public interface Schedulable {

	public void schedule( TaskScheduler taskScheduler );
}
