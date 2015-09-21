package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class TaskFactory 
extends SimpleFactory<Task>
implements Factory<Task> 
{
    TaskFactory ( ) 
    {
        super(Task.class, new TaskMapper ( ));
    }
	
	@Override
	public Task getInstance (Identificator<Task> id) 
	{
		// TODO
		return Task.EMPTY;
	}
	
	public Set<Task> getInstances (Identificator<Pool> idOfPool, int quantityOfTasksToBeFetched)
	{
		// TODO
		return java.util.Collections.<Task>emptySet( );
	}
	
	public Set<Task> getInstances (Identificator<Pool> idOfPool)
	{
		// TODO
		return java.util.Collections.<Task>emptySet( );
	}

	public Task newInstance(String descriptionOfTask, Integer idOfTaskType) {
		// TODO Auto-generated method stub
		return Task.EMPTY;
	}

	public Task update(Task task, String descriptionOfTask) {
		// TODO Auto-generated method stub
		return Task.EMPTY;
	}

	public boolean destroy(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Task> getInstances(Exam exam) {
		// TODO Auto-generated method stub
		return Collections.<Task>emptySet( );
	}
	
	public Set<Task> getAssessmentInstances (Identificator<Assessment> id)
	{
        // TODO Auto-generated method stub
        return Collections.<Task>emptySet( );
	}
}
