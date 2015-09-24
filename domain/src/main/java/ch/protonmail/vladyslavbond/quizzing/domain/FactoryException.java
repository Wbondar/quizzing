package ch.protonmail.vladyslavbond.quizzing.domain;

public abstract class FactoryException 
extends Exception 
{
	/**
     * 
     */
    private static final long serialVersionUID = -2138872788530145842L;

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
