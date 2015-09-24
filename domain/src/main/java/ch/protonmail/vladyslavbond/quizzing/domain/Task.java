package ch.protonmail.vladyslavbond.quizzing.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Task 
implements Identifiable<Task>
{
	public static final Task EMPTY = new Task ( );
	
	private Task ( )
	{
		this.id          = (Identificator<Task>)NumericIdentificator.<Task>valueOf(0);
		this.calculator  = new WrittenCommunicationRewardCalculator (this);
		this.description = "Description of the task is missing.";
		this.options     = Collections.<Option>emptySet( );
	}
	
	Task (Identificator<Task> id, Class<? extends RewardCalculator> typeOfCalculator, String description, Set<Option> options) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		this.id          = id;
		this.calculator  = typeOfCalculator.getDeclaredConstructor(Task.class).newInstance(this);
		this.description = description;
		this.options     = Collections.<Option>unmodifiableSet(options);
	}
	
	Task (Identificator<Task> id, Class<? extends RewardCalculator> calculator, String description) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		this(id, calculator, description, Collections.<Option>emptySet( ));
	}
	
	private final Identificator<Task> id;
	
	public Identificator<Task> getId ( )
	{
		return this.id;
	}
	
	private final String description;
	
	public final String getDescription ( )
	{
		return this.description;
	}
	
	private final Set<Option> options;
	
	/**
	 * Returns set of options associated with this task.
	 * Stores options in task object itself instead of 
	 * been a facade for options factory
	 * in order to guarantee data integrity
	 * while taking assessments.
	 * @return immutable set of options associated with this task.
	 */
	
	public final Set<Option> getOptions ( )
	{
		return this.options;
	}
	
	/**
	 * Injected bevahiour object for 
	 * processing provided answers,
	 * calculating amount of earned points,
	 * etc.
	 */
	
	private final RewardCalculator calculator;
	
	/**
	 * Facade for injected behaviour object.
	 * @param answer may be numeric, alphanumeric, URL or any other.
	 * @return true if provided answer was successfully processed.
	 */
	
	public final boolean provideAnswer (String answer)
	{
		return this.calculator.provideAnswer(answer);
	}
	
	/**
	 * Facade for injected behaviour object.
	 * @return quantity of earned points for this task. May be negative.
	 */
	
	public final Integer score ( )
	{
		return this.calculator.score( );
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o == this)
		{
		    return true;
		}
		if (o instanceof Task)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public final int hashCode ( )
	{
		return this.id.hashCode( ) + this.calculator.getId( ).toNumber( ).intValue( ) + this.description.hashCode( ) + this.options.hashCode( );
	}
}
