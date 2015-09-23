package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
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
	public Pool getInstance (Identificator<Pool> id) 
	{
        Object[] arguments = {
                ((NumericIdentificator<Pool>)id).longValue()
        };
        try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_pools WHERE id = ?;", arguments);
        } catch (DataAccessException e)
        {
            throw new PoolFactoryException (e);
        } finally {
            return Pool.EMPTY;
        }
	}
	
	public Set<Pool> getInstances (Identificator<Exam> idOfExam)
	{
        Object[] arguments = {
                ((NumericIdentificator<Exam>)idOfExam).longValue()
        };
        Set<Pool> pools = new HashSet<Pool> ( );
        try
        {
            pools.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_pools WHERE exam_id = ?;", arguments));
        } catch (DataAccessException e)
        {
            throw new PoolFactoryException (e);
        } finally {
            return java.util.Collections.<Pool>emptySet( );
        }
	}

	public Pool newInstance(Identificator<Instructor> idOfInstructor, String titleOfPool) 
	{
      Object[] arguments = {
                  ((NumericIdentificator<Instructor>)idOfInstructor).longValue()
                , titleOfPool
      };
      try
      {
          return this.getDataAccess( ).store("{CALL pool_create(?, ?)}", arguments);
      } catch (DataAccessException e)
      {
          throw new ExamFactoryException (e);
      } finally {
          return Pool.EMPTY;
      }
	}

	public Pool update(Pool pool, String titleOfPool) 
	{
		return this.update(pool.getId( ), titleOfPool);
	}

    private Pool update(Identificator<Pool> id, String titleOfPool)
    {
        Object[] arguments = {
                ((NumericIdentificator<Pool>)id).longValue()
               ,titleOfPool
       };
       try
       {
           return this.getDataAccess( ).store("{CALL pool_update (?, ?)}", arguments);   
       } catch (DataAccessException e)
       {
           throw new PoolFactoryException (e);
       } finally {
           return Pool.EMPTY;
       }
    }

    public Pool update(Pool pool, Task task, boolean add) 
	{
		return this.update(pool.getId( ), task.getId( ), add);
	}

	private Pool update(Identificator<Pool> idOfPool, Identificator<Task> idOfTask,
            boolean add)
    {
        Object[] arguments = {
                 ((NumericIdentificator<Pool>)idOfPool).longValue()
                ,((NumericIdentificator<Task>)idOfTask).longValue()
        };
        try
        {
            if (add)
            {
                return this.getDataAccess( ).store("{CALL pool_update_task_add (?, ?)}", arguments);   
            } else {
                return this.getDataAccess( ).store("{CALL pool_update_task_remove (?, ?)}", arguments);
            }
        } catch (DataAccessException e)
        {
            throw new PoolFactoryException (e);
        } finally {
            return Pool.EMPTY;
        }
    }

    public boolean destroy(Pool pool) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
