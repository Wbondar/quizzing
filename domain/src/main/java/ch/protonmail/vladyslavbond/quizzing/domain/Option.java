package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Option 
implements Identifiable<Option>
{
	public static final Option EMPTY = new Option ( );
	
	private Option ( ) 
	{
		this.id      = (Identificator<Option>)NumericIdentificator.<Option>valueOf(0);
		this.message = "Message of option is missing.";
		this.reward  = 0;
	}
	
	Option (Identificator<Option> id, String message, int reward)
	{
		this.id      = id;
		this.message = message;
		this.reward  = reward;
	}
	
	Option (Identificator<Option> id, String message, Integer reward)
	{
		this(id, message, reward.intValue( ));
	}
	
	private final int reward;
	
	public Integer getReward ( )
	{
		return Integer.valueOf(this.reward);
	}
	
	private final String message;
	
	public String getMessage ( )
	{
		return this.message;
	}
	
	public final Identificator<Option> id;
	
	@Override
	public Identificator<Option> getId ( )
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
		if (o instanceof Option)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.id.hashCode( ) + this.message.hashCode( ) + this.reward * this.reward;
	}
}
