package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class OptionFactory 
extends SimpleFactory<Option>
implements Factory<Option> 
{
    OptionFactory ( ) 
    {
        super(Option.class, new OptionMapper ( ));
    }
	
	@Override
	public Option getInstance (Identificator<Option> id) throws OptionFactoryException 
	{
	      Object[] arguments = {
	              ((NumericIdentificator<Option>)id).longValue()
	      };
	      try
	      {
	          return this.getDataAccess( ).fetch("SELECT * FROM view_options WHERE id = ?;", arguments);
	      } catch (DataAccessException | MapperException e)
	      {
	          throw new OptionFactoryException (e);
	      }
	}
	
	public Set<Option> getInstances (Identificator<Task> idOfTask) throws OptionFactoryException
	{
        Object[] arguments = {
                ((NumericIdentificator<Task>)idOfTask).longValue()
        };
        Set<Option> options = new HashSet<Option> ( );
        try
        {
            options.addAll(this.getDataAccess( ).fetchAll("SELECT * FROM view_options WHERE task_id = ?;", arguments));
        } catch (DataAccessException | MapperException e)
        {
            throw new OptionFactoryException (e);
        }
        return options;
	}

	public Option newInstance(Task task, String messageOfOption, Integer reward) throws OptionFactoryException 
	{
	    return this.newInstance(task.getId( ), messageOfOption, reward);
	}

	private Option newInstance(Identificator<Task> id, String messageOfOption,
            Integer reward) throws OptionFactoryException
    {
	      Object[] arguments = {
	              ((NumericIdentificator<Task>)id).longValue()
	              ,messageOfOption
	              ,reward
	      };
	      try
	      {
	          return this.getDataAccess( ).store("{CALL option_create (?, ?, ?)}", arguments);
	      } catch (DataAccessException | MapperException e)
	      {
	          throw new OptionFactoryException (e);
	      }
    }

    public Option update(Option option, Integer reward) throws OptionFactoryException 
	{
        return this.update(option.getId( ), reward);
	}

	private Option update(Identificator<Option> id, Integer reward) throws OptionFactoryException
    {
        Object[] arguments = {
                ((NumericIdentificator<Option>)id).longValue()
                ,reward
        };
        try
        {
            return this.getDataAccess( ).store("{CALL option_update_reward (?, ?)}", arguments);
        } catch (DataAccessException | MapperException e)
        {
            throw new OptionFactoryException (e);
        }
    }

	public Option update (Option option, String messageOfOption) throws OptionFactoryException 
	{
		return this.update(option.getId( ), messageOfOption);
	}

    private Option update(Identificator<Option> id, String messageOfOption) throws OptionFactoryException
    {
        Object[] arguments = {
                ((NumericIdentificator<Option>)id).longValue()
                ,messageOfOption
        };
        try
        {
            return this.getDataAccess( ).store("{CALL option_update_message (?, ?)}", arguments);
        } catch (DataAccessException | MapperException e)
        {
            throw new OptionFactoryException (e);
        }
    }

    public boolean destroy(Option option) 
    {
        // TODO Auto-generated method stub
        return false;
    }
}
