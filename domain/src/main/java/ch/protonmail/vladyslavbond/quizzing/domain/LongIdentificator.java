package ch.protonmail.vladyslavbond.quizzing.domain;

final class LongIdentificator<T>
implements Identificator<T>
{
	LongIdentificator (long id)
	{
		this.id = id;
	}
	
	LongIdentificator (Long id)
	{
		this(id.longValue( ));
	}
	
	private final long id;
	
	long longValue ( )
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
		if (o instanceof LongIdentificator)
		{
			return ((LongIdentificator)o).longValue( ) == this.id;
		}
		return false;
	}
	
	@Override
	public String toString ( )
	{
		return String.valueOf(this.id);
	}
}
