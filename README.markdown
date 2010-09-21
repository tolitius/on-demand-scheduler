## What is "On Demand Scheduler"? ##

Is a collection of self 'schedulable' tasks that are given a Spring's TaskScheduler will get scheduled on demand / programmatically.

## Why would I need it?##

Good question. Afterall, there is Spring's "&lt;task:scheduled&gt;", Quartz, JDK Timer, you name it...<br/><br/>
The idea behind "On Demand" scheduler is to achieve three things:<br/>

1. Be very simple
2. Be based on Spring
3. Be able to be schedule tasks on demand<br/>

Currently, the way to schedule a task in Spring is to use &lt;task:scheduled&gt; ( satisfies #1 and #2 ):

    <task:scheduled-tasks scheduler="myScheduler">
        <task:scheduled ref="someObject" method="someMethod" fixed-delay="5000"/>
    </task:scheduled-tasks>

    <task:scheduler id="myScheduler" pool-size="10"/>

Which means that the task is going to be scheduled _automatically_ when the application context is created.<br/>

"On Demand" scheduler compliments this ability with 'Schedulable' tasks that can be scheduled exactly _when needed_.

## Can you give me an example? ##

Business requirement: Whenever mail arrives, schedule a mail confirmation task.

    <bean id="deliveryTask" 
          class="org.gitpod.scheduler.task.FixDelayTask">
        
        <property name="delay" value="10000"/>
        <property name="targetObject" ref="mailer"/>
        <property name="methodName" value="confirmDelivery"/>        
    </bean>

That is pretty much it.<br/>
You would of course have an aspect after / around that certain method that needs attention ( e.g. mailHasArrived() ), and have a regular Spring TaskScheduler injected into the aspect.<br/><br/>
The main idea here is to have these simple tasks ( FixDelayTask, FixRateTask, CronTask, etc. ) that are suggested by Spring APIs ( e.g. take a look at Spring's ScheduledTaskRegistrar ), inject them in to components that need them as 'Schedulable's, and schedule whenever appropriate.

###### _TODO: Refactor to use FactoryBean(s), JavaDocs, more tests_
