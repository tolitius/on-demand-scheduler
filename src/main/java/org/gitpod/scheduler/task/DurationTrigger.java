package org.gitpod.scheduler.task;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Calendar;
import java.util.Date;

/**
 *  <p>TODO: add docs</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public class DurationTrigger implements Trigger {

    private final Date startTime;
    private final Date endTime;

    private volatile boolean fixedRate = false;

    private final long period;

    public DurationTrigger( Date startTime, Date endTime, long period ) {
        this.startTime =  startTime;
        this.endTime = endTime;
        this.period = period;
    }

    public Date nextExecutionTime(TriggerContext triggerContext) {

        long now = System.currentTimeMillis();

        // if we are before the cut off
        if ( now < endTime.getTime() ) {
            // and after the start time
            if ( now >= startTime.getTime() ) {
                if ( fixedRate ) {
                    return new Date( triggerContext.lastScheduledExecutionTime().getTime() + period );
                }
                else {
                    return new Date( triggerContext.lastCompletionTime().getTime() + period );
                }
            }
            else {
                return new Date( now + ( startTime.getTime() - now ) );
            }
        }

        return null;
    }

	/**
	 * Specify whether the periodic interval should be measured between the
	 * scheduled start times rather than between actual completion times.
	 * The latter, "fixed delay" behavior, is the default.
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
