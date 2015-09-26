package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Pool 
implements Identifiable<Pool>
{
	public final static Pool EMPTY = new Pool ( );
	
	private Pool ( )
	{
		this.id = NumericIdentificator.<Pool>valueOf(0);
		this.title = "Title of the pool is missing.";
	}
	
	Pool (Identificator<Pool> id, String title)
	{
		this.id = id;
		this.title = title;
	}
	
	public Set<Task> getTasks (int quantityOfTasksToBeFetched) throws TaskFactoryException
	{
		return Factories.<TaskFactory>getInstance(TaskFactory.class).getInstances(this, quantityOfTasksToBeFetched);
	}

    public Set<Task> getTasks() throws TaskFactoryException
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class).getInstances(this);
    }
	
	private final String title;
	
	public String getTitle ( )
	{
		return this.title;
	}
	
	private final Identificator<Pool> id;
	
	@Override
	public Identificator<Pool> getId ( ) 
	{
		return this.id;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o instanceof Pool)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.id.hashCode( );
	}
}
