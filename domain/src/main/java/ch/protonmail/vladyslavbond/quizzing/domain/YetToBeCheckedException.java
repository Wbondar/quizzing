package ch.protonmail.vladyslavbond.quizzing.domain;

public final class YetToBeCheckedException 
extends RewardCalculatorException 
{
	/**
     * 
     */
    private static final long serialVersionUID = -2781131907221812194L;

    YetToBeCheckedException ( )
	{
		super("Provided answer is yet to be checked by an instructor.");
	}
}
