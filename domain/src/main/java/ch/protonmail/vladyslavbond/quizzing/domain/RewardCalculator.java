package ch.protonmail.vladyslavbond.quizzing.domain;

abstract class RewardCalculator 
implements Identifiable<RewardCalculator> 
{
	RewardCalculator (Task taskToCalcualteGradeFor) 
	{
		this.task = taskToCalcualteGradeFor;
	}
	
	private final Task task;
	
	protected final Task getTask ( )
	{
		return this.task;
	}
	
	public abstract boolean provideAnswer (String answer);
	
	public abstract Integer score ( );
	
	public abstract java.util.Collection<String> getInput ( );
}
