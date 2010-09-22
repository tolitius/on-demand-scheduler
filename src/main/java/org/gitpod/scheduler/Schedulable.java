package org.gitpod.scheduler;

/**
 * <p> Enables schedulable behavior for task execution.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public interface Schedulable {

    /**
     * <p> Each schedulable task would define a strategy on how exactly it can be scheduled ( e.g. spawning a
     *     new thread,using some kind of a task scheduler, sending a claim check to channel, etc..)</p>
     *
     * <p> Once this method is called, the task would be scheduled, until it is canceled, e.g. a cancel method is
     *     called, this task or an underlined scheduler is destroyed, etc..</p>
     **/
	public void schedule();

    /**
     * <p> Each schedulable task would define a way to cancel itself. This is a cleanest way to cancel the task.</p>
     *
     * <p> Once this method is called, the task will be cancelled, which may or may not mean destroyed, as it can be
     *     later rescheduled using a schedule() method again.</p>
     **/
    public void cancel();
}
