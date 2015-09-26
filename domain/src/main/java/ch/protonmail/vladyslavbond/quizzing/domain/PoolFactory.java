package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class PoolFactory 
extends SimpleFactory<Pool>
implements Factory<Pool> 
{
    PoolFactory ( ) 
    {
        super(Pool.class, new PoolMapper ( ));
    }
	
	@Override
	public Pool getInstance (Identificator<Pool> id) throws PoolFactoryException 
	{
        Object[] arguments = {
                id.toNumber( ).intValue( )
        };
        try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_pools WHERE id = ?;", arguments);
        } catch (MapperException | DataAccessException e)
        {
            throw new PoolFactoryException (e);
        }
	}
	
	private Set<Pool> getInstances (Identificator<Exam> idOfExam) throws PoolFactoryException
	{
        Object[] arguments = {
                idOfExam.toNumber( ).intValue( )
        };
        Set<Pool> pools = new HashSet<Pool> ( );
        try
        {
            pools.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_exam_pools WHERE exam_id = ?;", arguments));
        } catch (MapperException | DataAccessException e)
        {
            throw new PoolFactoryException (e);
        }
        return pools;
	}
	
	public Set<Pool> getInstances (Exam exam) throws PoolFactoryException
	{
	    return getInstances(exam.getId( ));
	}

	private Pool newInstance(Identificator<Instructor> idOfInstructor, String titleOfPool) throws PoolFactoryException 
	{
      Object[] arguments = {
                 idOfInstructor.toNumber( ).intValue( )
                , titleOfPool
      };
      try
      {
          return this.getDataAccess( ).store("{CALL pool_create(?, ?)}", arguments);
      } catch (MapperException | DataAccessException e)
      {
          throw new PoolFactoryException (e);
      }
	}
	
	public Pool newInstance (Instructor instructor, String titleOfPool) throws PoolFactoryException
	{
	    return newInstance(instructor.getId( ), titleOfPool);
	}

	public Pool update(Instructor instructor, Pool pool, String titleOfPool) throws PoolFactoryException 
	{
		return this.update(instructor.getId( ), pool.getId( ), titleOfPool);
	}

    private Pool update(Identificator<Instructor> idOfInstructor, Identificator<Pool> id, String titleOfPool) throws PoolFactoryException
    {
        Object[] arguments = {
                idOfInstructor.toNumber().intValue( )
               ,((NumericIdentificator<Pool>)id).intValue()
               ,titleOfPool
       };
       try
       {
           return this.getDataAccess( ).store("{CALL pool_update (?, ?, ?)}", arguments);   
       } catch (MapperException | DataAccessException e)
       {
           throw new PoolFactoryException (e);
       }
    }

    public Pool update(Instructor instructor, Pool pool, Task task, boolean add) throws PoolFactoryException 
	{
		return this.update(instructor.getId( ), pool.getId( ), task.getId( ), add);
	}

	private Pool update(Identificator<Instructor> idOfInstructor, Identificator<Pool> idOfPool, Identificator<Task> idOfTask,
            boolean add) throws PoolFactoryException
    {
        Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).intValue()
                , ((NumericIdentificator<Pool>)idOfPool).intValue()
                , ((NumericIdentificator<Task>)idOfTask).intValue()
        };
        try
        {
            if (add)
            {
                return this.getDataAccess( ).store("{CALL pool_update_task_add (?, ?, ?)}", arguments);   
            } else {
                return this.getDataAccess( ).store("{CALL pool_update_task_remove (?, ?, ?)}", arguments);
            }
        } catch (MapperException | DataAccessException e)
        {
            throw new PoolFactoryException (e);
        }
    }
	
	private boolean destroy (Identificator<Instructor> idOfInstructor, Identificator<Pool> idOfPool)
	{
	    // TODO Implement deletion of a pool from the database.
	    return false;
	}

    public boolean destroy(Instructor instructor, Pool pool) 
	{
		return destroy(instructor.getId( ), pool.getId( ));
	}
}
