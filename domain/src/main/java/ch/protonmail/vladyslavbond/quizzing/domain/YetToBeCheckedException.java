package ch.protonmail.vladyslavbond.quizzing.domain;

public final class YetToBeCheckedException 
extends RewardCalculatorException 
{
	YetToBeCheckedException ( )
	{
		super("Provided answer is yet to be checked by an instructor.");
	}
}
