package org.gitpod.scheduler.task.factory;

import org.gitpod.scheduler.task.FixDelayTask;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.MethodInvokingRunnable;

/**
 *  <p>Abstract factory bean that encapsulates a {@link org.springframework.scheduling.TaskScheduler},
 *     and is responsible for creating a Runnable task.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public abstract class AbstractSchedulableTaskFactoryBean extends MethodInvokingRunnable
                      implements FactoryBean<FixDelayTask>, DisposableBean {

    private TaskScheduler scheduler;

    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException {

        super.afterPropertiesSet();
        
        if ( scheduler == null ) {
            throw new IllegalArgumentException( "'scheduler must be set" );
        }
    }

    public boolean isSingleton() {
        return false;
    }

    protected TaskScheduler getScheduler() {
        return scheduler;    
    }

    public void setScheduler( TaskScheduler scheduler ) {
        this.scheduler = scheduler;
    }
}
