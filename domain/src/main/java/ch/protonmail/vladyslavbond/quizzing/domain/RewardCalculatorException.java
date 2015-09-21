package ch.protonmail.vladyslavbond.quizzing.domain;

public abstract class RewardCalculatorException 
extends RuntimeException 
{
	/**
     * 
     */
    private static final long serialVersionUID = 8649814376236871186L;

    RewardCalculatorException (String message, Throwable cause)
	{
		super(message, cause);
	}
	
	RewardCalculatorException (String message)
	{
		super(message);
	}
}
