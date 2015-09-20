package ch.protonmail.vladyslavbond.quizzing.domain;

public final class Factories 
{
	public static <T extends Factory<?>> T getInstance (Class<T> typeOfFactory)
	{
		try
		{
			return typeOfFactory.newInstance( );
		} catch (Exception e) {
			throw new FactoryInstantiationFailedException (typeOfFactory, e);
		}
	}
	
	private Factories ( ) {}
}
