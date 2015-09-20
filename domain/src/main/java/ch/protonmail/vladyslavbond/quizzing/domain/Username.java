package ch.protonmail.vladyslavbond.quizzing.domain;

public final class Username 
implements Identificator<Member> 
{
	Username (String username)
	{
		this.username = username.toLowerCase( );
	}
	
	private final String username;
	
	@Override
	public String toString ( )
	{
		return this.username;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o instanceof Username)
		{
			return o.toString( ).equals(this.toString( ));
		}
		return false;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.username.hashCode( );
	}
}
