package org.gitpod.scheduler.trigger;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

/**
 * <p> A trigger that would execute a task periodically within a specified time window: from "start time" to
 *     "end time".</p>
 *
 * <p> The period may be applied as fixed-rate, which is <code>false</code> by default, meaning that the period is
 *     measured from each <emphasis>completion</emphasis> time. To measure the interval between the scheduled
 *     <emphasis>start</emphasis> time of each execution instead, set the 'fixedRate' property to <code>true</code>.<p>
 *
 * <p> Note that the TaskScheduler interface already defines methods for scheduling tasks at fixed-rate.</p>
 *
 * <p> The value of this Trigger implementation is that it can be used within components that rely on the Trigger
 *     abstraction. For example, it may be convenient to allow periodic triggers, cron-based triggers, and even custom
 *     Trigger implementations to be used interchangeably.
 *
 *  @author anatoly.polinsky
 *
 **/
public class DurationTrigger implements Trigger {

    private final Date startTime;
    private final Date endTime;

    private volatile boolean fixedRate = false;

    private final long period;

    /**
     * <p> Create a trigger with the given period, start and end time that define a time window that a task will be
     *     scheduled within.</p>
     */
    public DurationTrigger( Date startTime, Date endTime, long period ) {
        this.startTime =  startTime;
        this.endTime = endTime;
        this.period = period;
    }

    /**
     * <p> Returns the time after which a task should run again. </p>
     */
    public Date nextExecutionTime(TriggerContext triggerContext) {

        long now = System.currentTimeMillis();

        // if we are before the cut off
        if ( now < endTime.getTime() ) {
            // and after the start time
            if ( now >= startTime.getTime() ) {
                if ( ! fixedRate ) {
                    return new Date( triggerContext.lastCompletionTime().getTime() + period );
                }
                else {
                    return new Date( triggerContext.lastScheduledExecutionTime().getTime() + period );

                }
            }
            else {
                return new Date( now + ( startTime.getTime() - now ) );
            }
        }

        return null;
    }

	/**
	 * <p> Specify whether the periodic interval should be measured between the scheduled start times rather than
     *     between actual completion times. The latter, "fixed delay" behavior, is the default.</p>
	 */
	public void setFixedRate( boolean fixedRate ) {
		this.fixedRate = fixedRate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DurationTrigger)) {
			return false;
		}
		DurationTrigger other = (DurationTrigger) obj;
		return this.period == other.period
				&& this.startTime.equals( other.startTime )
				&& this.endTime.equals( other.endTime );
	}

	@Override
	public int hashCode() {
		return (int) ( this.period * 29 ) +
			   (int) ( 37 * this.startTime.getTime() ) +
			   (int) ( 19 * this.endTime.getTime() );
	}
}
