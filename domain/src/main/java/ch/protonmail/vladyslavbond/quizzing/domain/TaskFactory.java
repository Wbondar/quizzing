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

    private Task newInstance(Identificator<Instructor> idOfInstructor, TaskType taskType, String description)
        throws TaskFactoryException
    {
        Object[] arguments = {
                  idOfInstructor.toNumber( ).intValue( )
                , taskType.getId( ).toNumber( ).shortValue( )
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

    public Task newInstance(Instructor instructor, TaskType taskType, String description) 
            throws TaskFactoryException
    {
        return this.newInstance(instructor.getId( ), taskType, description);
    }

    public Set<Task> getOngoingAssessmentInstances(Identificator<OngoingAssessment> id) throws TaskFactoryException
    {
        return this.getAssessmentInstances(id.toNumber( ).longValue( ));
    }

    public Set<Task> getFinishedAssessmentInstances(Identificator<FinishedAssessment> id) throws TaskFactoryException
    {
        return this.getAssessmentInstances(id.toNumber( ).longValue( ));
    }

    public Set<Task> getInstances(OngoingAssessment assessment) 
            throws TaskFactoryException
    {
        return this.getOngoingAssessmentInstances(assessment.getId( ));
    }

    public Set<Task> getInstances(FinishedAssessment assessment) 
            throws TaskFactoryException
    {
        return this.getFinishedAssessmentInstances(assessment.getId( ));
    }

    private Set<Task> getPoolInstances(Identificator<Pool> id, int quantity) 
            throws TaskFactoryException
    {
        if (quantity < 1)
        {
            quantity = 1;
        }
        Object[] arguments = {
              id.toNumber( ).intValue( )
        };
        Set<Task> tasks = new HashSet<Task> ( );
        try
        {
            tasks.addAll(this.getDataAccess( ).fetchAll(String.format("SELECT * FROM view_pool_tasks WHERE pool_id = ? ORDER BY RANDOM() LIMIT %d;", quantity), arguments));
        } 
        catch (MapperException | DataAccessException e)
        {
            throw new TaskFactoryException ("Failed retrieve tasks by pool.", e);
        }
        return tasks;
    }

    public Set<Task> getInstances(Pool pool, int quantityOfTasksToBeFetched) 
            throws TaskFactoryException
    {
        return getPoolInstances(pool.getId( ), quantityOfTasksToBeFetched);
    }
    
    private Set<Task> getAllPoolInstances (Identificator<Pool> idOfPool) throws TaskFactoryException
    {
        Object[] arguments = {
                idOfPool.toNumber( ).intValue( )
          };
          Set<Task> tasks = new HashSet<Task> ( );
          try
          {
              tasks.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_pool_tasks WHERE pool_id = ?;", arguments));
          } 
          catch (MapperException | DataAccessException e)
          {
              throw new TaskFactoryException ("Failed retrieve tasks by pool.", e);
          }
          return tasks; 
    }
    
    public Set<Task> getInstances (Pool pool) throws TaskFactoryException
    {
        return getAllPoolInstances(pool.getId( ));
    }
}
