package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
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
	public Task getInstance (Identificator<Task> id) throws TaskFactoryException 
	{
		Object[] arguments = {((NumericIdentificator<Task>)id).intValue( )};
		try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_tasks WHERE id = ?", arguments);
        } catch (DataAccessException | MapperException e)
        {
            throw new TaskFactoryException ("Failed to retrieve task by id.", e);
        }
	}

	public Task update (Instructor updater, Task task, String descriptionOfTask) throws TaskFactoryException 
	{
	    return this.update(updater.getId( ), task.getId( ), descriptionOfTask);
	}

	private Task update(Identificator<Instructor> idOfInstructor, Identificator<Task> idOfTask, String descriptionOfTask) throws TaskFactoryException
    {
        Object[] arguments = {
                 ((NumericIdentificator<Instructor>)idOfInstructor).intValue()
               , ((NumericIdentificator<Task>)idOfTask).intValue()
               , descriptionOfTask
       };
       try
       {
           return this.getDataAccess( ).store("{CALL task_update (?, ?, ?)}", arguments);   
       } catch (MapperException | DataAccessException e)
       {
           throw new TaskFactoryException (e);
       } 
    }

    public boolean destroy(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<Task> getInstances(Exam exam) throws TaskFactoryException 
  {
		return this.getExamInstances(exam.getId( ));
	}

  Set<Task> getExamInstances (Identificator<Exam> idOfExam) throws TaskFactoryException
  {
    // TODO The following code is just a placeholder!
      Object[] arguments = {
                ((NumericIdentificator<Exam>)idOfExam).intValue( )
      };
      Set<Task> tasks = new HashSet<Task> ( );
      try
      {
          tasks.addAll(getDataAccess( ).fetchAll("SELECT * FROM view_tasks;", arguments));
          return tasks;
      } catch (MapperException | DataAccessException e) {
          throw new TaskFactoryException ("Failed to fetch tasks by exam.", e);
      }
  }
	
    Set<Task> getAssessmentInstances (long id) throws TaskFactoryException
	{
        Set<Task> tasks = new HashSet<Task> ( );
        try
        {
            tasks.addAll(getDataAccess( ).fetchAll("SELECT * FROM view_assessment_tasks WHERE assessment_id = ?;", id));
            return tasks;
        } catch (MapperException | DataAccessException e) {
            throw new TaskFactoryException ("Failed to fetch tasks by assessment.", e);
        }
	}

    public Task newInstance(Identificator<Instructor> idOfInstructor, TaskType taskType, String description)
        throws TaskFactoryException
    {
        Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).intValue( )
                , ((NumericIdentificator<TaskType>)taskType.getId( )).shortValue( )
                , description
        };
        try
        {
            return this.getDataAccess( ).store("{CALL task_create(?, ?, ?)}", arguments);
        } catch (MapperException | DataAccessException e)
        {
            throw new TaskFactoryException ("Failed to create new task.", e);
        }
    }

    public Set<Task> getOngoingAssessmentInstances(Identificator<OngoingAssessment> id) throws TaskFactoryException
    {
        return this.getAssessmentInstances(((NumericIdentificator<OngoingAssessment>)id).longValue( ));
    }

    public Set<Task> getFinishedAssessmentInstances(Identificator<FinishedAssessment> id) throws TaskFactoryException
    {
        return this.getAssessmentInstances(((NumericIdentificator<FinishedAssessment>)id).longValue( ));
    }

    public Set<Task> getInstances(Identificator<Pool> id,
            int quantityOfTasksToBeFetched) throws TaskFactoryException
    {
        // TODO Auto-generated method stub
        return this.getInstances(Exam.EMPTY);
    }
}
