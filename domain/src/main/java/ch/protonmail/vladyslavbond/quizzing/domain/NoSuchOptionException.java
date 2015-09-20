package ch.protonmail.vladyslavbond.quizzing.domain;

public final class NoSuchOptionException 
extends RewardCalculatorException 
{
	NoSuchOptionException ( )
	{
		super("Provided answer does not correspond to any of available options.");
	}
}
