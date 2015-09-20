package ch.protonmail.vladyslavbond.quizzing.domain;

public abstract class FactoryException 
extends RuntimeException 
{
	FactoryException (String message, Throwable cause)
	{
		super (message, cause);
	}
	
	FactoryException (String message)
	{
		super(message);
	}
	
	FactoryException (Throwable cause)
	{
		super(cause);
	}
}
