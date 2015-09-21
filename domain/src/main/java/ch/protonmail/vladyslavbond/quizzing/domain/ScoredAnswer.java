package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class ScoredAnswer 
extends Answer 
{
	public final static ScoredAnswer EMPTY = new ScoredAnswer ( );

	private ScoredAnswer ( )
	{
		this(Answer.EMPTY, 0);
	}
	
	ScoredAnswer (Answer answer, int reward)
	{
		this(answer.getId( ), answer.getTask( ), answer.getStudent( ), answer.getInput( ), reward);
	}
	
	ScoredAnswer (Identificator<Answer> id, Task task, Student student, String input, int reward)
	{
		super(id, task, student, input);
		this.reward = reward;
	}
	
	private final int reward;
	
	public final Integer getReward ( )
	{
		return Integer.valueOf(this.reward);
	}
	
	@Override
	public int hashCode ( )
	{
		return super.hashCode( ) + this.reward * this.reward;
	}
}
