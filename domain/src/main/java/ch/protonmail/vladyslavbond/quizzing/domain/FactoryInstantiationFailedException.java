package ch.protonmail.vladyslavbond.quizzing.domain;

public class FactoryInstantiationFailedException 
extends FactoryException 
{
	/**
     * 
     */
    private static final long serialVersionUID = -6782371725179580440L;

    FactoryInstantiationFailedException (Class<? extends Factory<?>> typeOfFactory, Throwable cause)
	{
		super(String.format("Failed to get instance of factory of type %s.", typeOfFactory.toString( )), cause);
	}
}
