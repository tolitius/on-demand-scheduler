package org.gitpod.scheduler.task.factory;

import org.gitpod.scheduler.task.FixDelayTask;

import java.util.Date;

/**
 *  <p>FactoryBean for creating {@link org.gitpod.scheduler.task.FixDelayTask}</p>
 *
 *  @author anatoly.polinsky
 *  
 **/
public class FixDelayTaskFactoryBean extends AbstractSchedulableTaskFactoryBean {

    private FixDelayTask fixDelayTask;

    private Long delay = -1L;
    private Date startTime;

    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException {

        super.afterPropertiesSet();

        if ( delay < 0 ) {
            throw new IllegalArgumentException( "'delay' must be set and be positive" );
        }

        fixDelayTask = new FixDelayTask( this, delay, startTime, getScheduler() );
    }

    public void destroy() throws Exception {
        fixDelayTask.cancel();
    }

    public FixDelayTask getObject() throws Exception {
        return fixDelayTask;
    }

    public Class<FixDelayTask> getObjectType() {
        return FixDelayTask.class;
    }


    // public accessors
    public void setDelay( Long delay ) {
        this.delay = delay;
    }

    public void setStartTime( Date startTime ) {
        this.startTime = startTime;
    }
}
