package ch.protonmail.vladyslavbond.quizzing.domain;

public final class OngoingAssessment 
extends Assessment 
{
	public static final OngoingAssessment EMPTY = new OngoingAssessment ( );
	
	private OngoingAssessment ( )
	{
		this(Assessment.EMPTY);
	}
	
	OngoingAssessment (Assessment assessment)
	{
		super(assessment.getId( ), assessment.getStudent( ), assessment.getTasks( ));
	}
	
	public final Answer provideAnswer (Task task, String answer)
	{
		return this.provideAnswer(task.getId( ), answer);
	}
	
	public final Answer provideAnswer (Identificator<Task> idOfTask, String answer)
	{
		for (Task task : this.getTasks( ))
		{
			if (task.getId( ).equals(idOfTask))
			{
				if (task.provideAnswer(answer))
				{
					AnswerFactory answerFactory = Factories.<AnswerFactory>getInstance(AnswerFactory.class);
					return answerFactory.newInstance(this, task, task.getInput( ));
				}
				return Answer.EMPTY;
			}
		}
		return Answer.EMPTY;
	}
	
	public final ScoredAnswer score (Identificator<Task> idOfTask)
	{
		for (Task task : this.getTasks( ))
		{
			if (task.getId( ).equals(idOfTask))
			{
				AnswerFactory answerFactory = Factories.<AnswerFactory>getInstance(AnswerFactory.class);
				return answerFactory.newInstance(this, task, task.score( ));
			}
		}
		return ScoredAnswer.EMPTY;
	}
	
	public final void finish ( )
	{
		
	}
}
