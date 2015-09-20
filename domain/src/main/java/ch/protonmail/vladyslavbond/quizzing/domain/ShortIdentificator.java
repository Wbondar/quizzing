package ch.protonmail.vladyslavbond.quizzing.domain;

final class ShortIdentificator<T>
implements Identificator<T>
{
	ShortIdentificator (short id)
	{
		this.id = id;
		this.string = String.valueOf((int)id);
	}
	
	ShortIdentificator (Short id)
	{
		this.id = id.shortValue( );
		this.string = id.toString( );
	}
	
	ShortIdentificator (String id)
	{
		this.id = Short.valueOf(id);
		this.string = id;
	}
	
	private final short id;
	
	short shortValue ( )
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
		if (o instanceof ShortIdentificator)
		{
			return ((ShortIdentificator)o).shortValue( ) == this.id;
		}
		return false;
	}
	
	private final String string;
	
	@Override
	public String toString ( )
	{
		return this.string;
	}
}
