package org.gitpod.scheduler.task.factory;

import org.gitpod.scheduler.task.FixDelayTask;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.MethodInvokingRunnable;

/**
 *  <p>Factory bean that creates a Runnable task from
 *     {@link org.springframework.scheduling.support.MethodInvokingRunnable}}.</p>
 *
 *  @author anatoly.polinsky
 *
 **/
public class RunnableTaskFactoryBean extends MethodInvokingRunnable implements FactoryBean<Runnable> {


    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException, IllegalArgumentException {
        super.afterPropertiesSet();
    }

    public Runnable getObject() throws Exception {
        return this;
    }

    public Class<MethodInvokingRunnable> getObjectType() {
        return MethodInvokingRunnable.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
