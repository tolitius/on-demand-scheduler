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

Business requirement: Whenever certain business service is done, schedule a pickup task ( e.g. to start picking up files every 5 seconds ).

    <bean id="filePickupTask" 
          class="org.gitpod.scheduler.task.factory.FixDelayTaskFactoryBean">
        
        <property name="delay" value="5000"/>
        <property name="targetObject" ref="fileGrabber"/>
        <property name="targetMethod" value="grab"/>
        <property name="scheduler" ref="taskScheduler"/>
    </bean>

That is pretty much it.<br/><br/>
This will create an immutable task that can be injected anywhere and can be scheduled as:

    deliveryTask.schedule();

To fully satisfy the above business requirement, you would of course have an after / around advice applied to that "certain method" that needs attention ( e.g. businessService() ), and have this task injected into the aspect.
***
Another example: Start process all the shipments of Ubuntu DVDs to Apple Headquarters every 5 minutes while the store is closed e.g. from 21:00 to 08:00

    <bean id="shipOrder"
          class="org.gitpod.scheduler.task.factory.RunnableTaskFactoryBean">

        <property name="targetObject" ref="shipper"/>
        <property name="targetMethod" value="ship"/>
        <property name="arguments">
            <util:list>
                <value>Ubuntu DVD</value>
                <value>
                    Apple Computer Inc 
                    1 Infinite Loop
                    Cupertino, CA 95014</value>
            </util:list>
        </property>
    </bean>

Again, this is a simple Spring bean that can be injected anywhere, and the Spring's TaskScheduler APIs can be used. e.g. here is an example from "SchedulingRunnableTaskWithTriggerIntegrationTest":

    @Before
    public void shouldCreateTriggerAndScheduleTask() {

        long now = Calendar.getInstance().getTime().getTime();

        // Set a trigger to start in 5 second, run every 2 seconds for 15 seconds:
        Date startTime =  new Date( now + 5000 );
        Date endTime =  new Date( now + 15000 );
        long period = 2000;

        Trigger trigger = new DurationTrigger( startTime, endTime, period );

        taskScheduler.schedule( shipOrderTask, trigger );
    }

##### where "org.gitpod.scheduler.task.DurationTrigger" is a custom trigger. You can simply grab it form sources.
***
The main idea here is to have these simple tasks: FixDelayTask, TimeOutTask, RunnableTask, FixRateTask, CronTask, etc., some of them are suggested by Spring APIs ( e.g. take a look at Spring's ScheduledTaskRegistrar ), inject them in to components that need them as 'Schedulable's, and schedule whenever appropriate.

###### _TODO: more tasks, more tests, more love_
