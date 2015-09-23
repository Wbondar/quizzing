package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

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
		Object[] arguments = {((NumericIdentificator<Task>)id).longValue( )};
		try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_tasks WHERE id = ?", arguments);
        } catch (DataAccessException e)
        {
            throw new TaskFactoryException ("Failed to retrieve task by id.", e);
        }
	}

	public Task update (Instructor updater, Task task, String descriptionOfTask) 
	{
	    return this.update(updater.getId( ), task.getId( ), descriptionOfTask);
	}

	private Task update(Identificator<Instructor> idOfInstructor, Identificator<Task> idOfTask, String descriptionOfTask)
    {
        Object[] arguments = {
                 ((NumericIdentificator<Instructor>)idOfInstructor).longValue()
               , ((NumericIdentificator<Task>)idOfTask).longValue()
               , descriptionOfTask
       };
       try
       {
           return this.getDataAccess( ).store("{CALL task_update (?, ?, ?)}", arguments);   
       } catch (DataAccessException e)
       {
           throw new TaskFactoryException (e);
       } finally {
           return Task.EMPTY;
       }
    }

    public boolean destroy(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Task> getInstances(Exam exam) 
  {
		return this.getExamInstances(exam.getId( ));
	}

  Set<Task> getExamInstances (Identificator<Exam> idOfExam)
  {
    // TODO The following code is just a placeholder!
      Object[] arguments = {
                ((NumericIdentificator<Exam>)idOfExam).longValue( )
      };
      Set<Task> tasks = new HashSet<Task> ( );
      try
      {
          tasks.addAll(getDataAccess( ).fetchAll("SELECT * FROM view_tasks;", arguments));
          return tasks;
      } catch (DataAccessException e) {
          throw new TaskFactoryException ("Failed to fetch tasks by exam.", e);
      } finally {
          return tasks;
      }
  }
	
    Set<Task> getAssessmentInstances (long id)
	{
        Set<Task> tasks = new HashSet<Task> ( );
        try
        {
            tasks.addAll(getDataAccess( ).fetchAll("SELECT * FROM view_assessment_tasks WHERE assessment_id = ?;", id));
            return tasks;
        } catch (DataAccessException e) {
            throw new TaskFactoryException ("Failed to fetch tasks by assessment.", e);
        } finally {
            return tasks;
        }
	}

    public Task newInstance(Identificator<Instructor> idOfInstructor, TaskType taskType, String description)
    {
        Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).longValue( )
                , ((NumericIdentificator<TaskType>)taskType.getId( )).intValue( )
                , description
        };
        try
        {
            return this.getDataAccess( ).store("{CALL task_create (?, ?, ?)}", arguments);
        } catch (DataAccessException e)
        {
            throw new TaskFactoryException ("Failed to create new task.", e);
        } finally {
            return Task.EMPTY;
        }
    }

    public Set<Task> getOngoingAssessmentInstances(Identificator<OngoingAssessment> id)
    {
        return this.getAssessmentInstances(((NumericIdentificator<OngoingAssessment>)id).longValue( ));
    }

    public Set<Task> getFinishedAssessmentInstances(Identificator<FinishedAssessment> id)
    {
        return this.getAssessmentInstances(((NumericIdentificator<FinishedAssessment>)id).longValue( ));
    }

    public Set<Task> getInstances(Identificator<Pool> id,
            int quantityOfTasksToBeFetched)
    {
        // TODO Auto-generated method stub
        return this.getInstances(Exam.EMPTY);
    }
}
