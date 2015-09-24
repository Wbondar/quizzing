package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class OngoingAssessment 
extends Object
implements Assessment, Identifiable<OngoingAssessment>
{
	public static final OngoingAssessment EMPTY = new OngoingAssessment ( );
	
    private final Identificator<OngoingAssessment> id;
    private final Student student;
	
	private OngoingAssessment ( )
	{
	    this(NumericIdentificator.<OngoingAssessment>valueOf(0L), Student.EMPTY);
	}
	
	public OngoingAssessment (Identificator<OngoingAssessment> id, Student student)
	{
	    this.id = id;
	    this.student = student;
	}
	
	private final Answer provideAnswer (Task task, String input) throws AnswerFactoryException
	{
        if (task.provideAnswer(input))
        {
            AnswerFactory answerFactory = Factories.<AnswerFactory>getInstance(AnswerFactory.class);
            return answerFactory.newInstance(this, task, input);
        }
        return Answer.EMPTY;
	}
	
	public final Answer provideAnswer (Identificator<Task> idOfTask, String input) throws AnswerFactoryException, TaskFactoryException
	{
		for (Task task : this.getTasks( ))
		{
			if (task.getId( ).equals(idOfTask))
			{
                return this.provideAnswer(task, input);
			}
		}
		return Answer.EMPTY;
	}
	
	/**
	 * Ensure that task belongs to the assessment before calling.
	 * @param task
	 * @return
	 * @throws ScoreFactoryException 
	 */
	private final Score score (Task task) throws ScoreFactoryException
	{
	    ScoreFactory scoreFactory = Factories.<ScoreFactory>getInstance(ScoreFactory.class);
	    Score score = scoreFactory.newInstance(this, task, task.score( ));
	    if (score != null && !score.equals(Score.EMPTY))
	    {
	        return score;
	    }
        return Score.EMPTY;
	}
	
	public final boolean finish ( ) throws AssessmentFactoryException, ScoreFactoryException, TaskFactoryException
	{
		for (Task task : this.getTasks( ))
		{
		    this.score(task);
		}
		OngoingAssessmentFactory assessmentFactory = Factories.<OngoingAssessmentFactory>getInstance(OngoingAssessmentFactory.class);
		return assessmentFactory.destroy(this);
	}

    @Override
    public Identificator<OngoingAssessment> getId()
    {
        return this.id;
    }

    @Override
    public Student getStudent()
    {
        return this.student;
    }

    @Override
    public Set<Task> getTasks() throws TaskFactoryException
    {
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        /**
         * Tasks are not stored in the instance 
         * in order to ensure data integrity with the database
         * in cost of performance.
         * May be changed in the future.
         */
        return taskFactory.getOngoingAssessmentInstances(this.id);
    }

    @Override
    public Set<Answer> getAnswers()
    {
        AnswerFactory answerFactory = Factories.<AnswerFactory>getInstance(AnswerFactory.class);
        return answerFactory.getOngoingAssessmentInstances(this.id);
    }

    @Override
    public Set<Answer> getAnswers(Task task)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
