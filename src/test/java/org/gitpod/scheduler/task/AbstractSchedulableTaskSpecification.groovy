package org.gitpod.scheduler.task

import org.gitpod.scheduler.task.AbstractSchedulableTask
import java.util.concurrent.ScheduledFuture
import spock.lang.Specification

class AbstractSchedulableTaskSpecification extends Specification {
	def task = new TestTask()
	def scheduledFuture = Mock( ScheduledFuture )
	
	def "should cancel the task"() {
		task.scheduledFuture = scheduledFuture
		
		when: "the task is canceled"
		task.cancel()
		
		then: 
		1 * scheduledFuture.cancel( true )	
	}
	
	def "should NOT cancel the task because it is NOT scheduled"() {
		when: "the task is canceled"
		task.cancel()
		
		then:
		0 * scheduledFuture.cancel( true )
	}
	
	def "task should be scheduled"() {
		task.scheduledFuture = scheduledFuture
		
		when: "checking if the task is scheduled"
		def isScheduled = task.isScheduled()
		
		then:
		1 * scheduledFuture.isCancelled()
		1 * scheduledFuture.isDone()
		
		expect: "the task is scheduled"
		isScheduled == true
	}
	
	def "task should NOT be scheduled because schedule is cancelled"() {
		task.scheduledFuture = scheduledFuture
		
		when: "checking if the task is scheduled"
		def isScheduled = task.isScheduled()
		
		then:
		1 * scheduledFuture.isCancelled() >> true
		
		expect:
		isScheduled == false
	}
	
	def "task should NOT be scheduled because schedule id done"() {
		task.scheduledFuture = scheduledFuture
		
		when: "checking if the task is scheduled"
		def isScheduled = task.isScheduled()
		
		then:
		1 * scheduledFuture.isCancelled()
		1 * scheduledFuture.isDone() >> true
		
		expect:
		isScheduled == false
	}
	
	def "task should NOT be scheduled because there is no schedule specified"() {
		when: "checking if he task is schedule"
		def isScheduled = task.isScheduled()
		
		then:
		0 * scheduledFuture.isCancelled()
		0 * scheduledFuture.isDone() >> true
		
		expect:
		isScheduled == false
	}
	
	class TestTask extends AbstractSchedulableTask {
		void schedule() {
			
		}
	}
	
}

