package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;

abstract class RewardCalculator 
implements Identifiable<RewardCalculator> 
{
    private final Task task;
    
	RewardCalculator (Task taskToCalcualteGradeFor) 
	{
		this.task = taskToCalcualteGradeFor;
	}
	
	protected final Task getTask ( )
	{
		return this.task;
	}
	
	public abstract boolean provideAnswer (String answer);
	
	public abstract Integer score ( );
	
	public abstract java.util.Collection<String> getInput ( );
}
