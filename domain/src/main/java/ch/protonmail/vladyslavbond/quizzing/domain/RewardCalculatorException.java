package ch.protonmail.vladyslavbond.quizzing.domain;

public abstract class RewardCalculatorException 
extends RuntimeException 
{
	RewardCalculatorException (String message, Throwable cause)
	{
		super(message, cause);
	}
	
	RewardCalculatorException (String message)
	{
		super(message);
	}
}
