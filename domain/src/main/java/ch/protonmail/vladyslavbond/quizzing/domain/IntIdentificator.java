package ch.protonmail.vladyslavbond.quizzing.domain;

final class IntIdentificator<T>
implements Identificator<T>
{
	IntIdentificator (int id)
	{
		this.id = id;
	}
	
	IntIdentificator (Integer id)
	{
		this(id.intValue( ));
	}
	
	IntIdentificator (String id)
	{
		this(Integer.valueOf(id));
	}
	
	private final int id;
	
	int intValue ( )
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
		if (o instanceof IntIdentificator)
		{
			return ((IntIdentificator)o).intValue( ) == this.id;
		}
		return false;
	}
	
	@Override
	public String toString ( )
	{
		return String.valueOf(this.id);
	}
}
