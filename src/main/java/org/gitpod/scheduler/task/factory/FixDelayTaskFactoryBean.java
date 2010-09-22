package org.gitpod.scheduler.task.factory;

import org.gitpod.scheduler.task.FixDelayTask;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.MethodInvokingRunnable;

import java.util.Date;

/**
 *  <p>FactoryBean for creating {@link org.gitpod.scheduler.task.FixDelayTask}</p>
 *
 *  @author anatoly.polinsky
 *  
 **/
public class FixDelayTaskFactoryBean implements
        FactoryBean<FixDelayTask>, InitializingBean, DisposableBean {

    private FixDelayTask fixDelayTask;

    private Long delay = -1L;
    private Date startTime;

    private String methodName;
    private Object targetObject;
    private Object[] arguments;

    private TaskScheduler scheduler;

    public void afterPropertiesSet() throws Exception {

        if ( delay < 0 ) {
            throw new IllegalArgumentException( "'delay' must be set and be positive" );
        }

        if ( scheduler == null ) {
            throw new IllegalArgumentException( "'scheduler' must be set" );
        }

        MethodInvokingRunnable task = new MethodInvokingRunnable();
        task.setTargetObject( targetObject );
        task.setTargetMethod( methodName );
        task.setArguments( arguments );

        task.afterPropertiesSet();

        fixDelayTask = new FixDelayTask( task, delay, startTime, scheduler );
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

    public boolean isSingleton() {
        return false;
    }

    // public accessors
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

    public void setScheduler( TaskScheduler scheduler ) {
        this.scheduler = scheduler;
    }
}
