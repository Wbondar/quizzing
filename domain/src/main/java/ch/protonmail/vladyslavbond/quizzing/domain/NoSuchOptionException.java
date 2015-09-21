package ch.protonmail.vladyslavbond.quizzing.domain;

public final class NoSuchOptionException 
extends RewardCalculatorException 
{
	/**
     * 
     */
    private static final long serialVersionUID = 4959371012894344841L;

    NoSuchOptionException ( )
	{
		super("Provided answer does not correspond to any of available options.");
	}
}
