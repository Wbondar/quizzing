package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class FinishedAssessment 
implements Assessment, Identifiable<FinishedAssessment>
{
	public static final FinishedAssessment EMPTY = new FinishedAssessment ( );

    private final Identificator<FinishedAssessment> id;;
    private final Student student;
    private final Set<Task> tasks;
    private final Set<Answer> answers;
    private final Set<Score> scores;
    
	private FinishedAssessment ( )
	{
		this.id      = NumericIdentificator.<FinishedAssessment>valueOf(0L);
		this.student = Student.EMPTY;
		this.tasks   = Collections.<Task>emptySet( );
		this.answers = Collections.<Answer>emptySet( );
		this.scores = Collections.<Score>emptySet( );
	}
	
	FinishedAssessment (Identificator<FinishedAssessment> id, Student student, Set<Task> tasks, Set<Answer> answers, Set<Score> scores)
	{
		this.id      = id;
		this.student = student;
		this.tasks   = Collections.<Task>unmodifiableSet(tasks);
		this.answers = Collections.<Answer>unmodifiableSet(answers);
		this.scores  = Collections.<Score>unmodifiableSet(scores);
	}
	
	@Override
	public final Student getStudent ( )
	{
		return this.student;
	}

	@Override
	public final Set<Task> getTasks ( )
	{
		return this.tasks;
	}
	
	@Override
	public final Set<Answer> getAnswers ( )
	{
	    return this.answers;
	}
	
	@Override
	public Identificator<FinishedAssessment> getId ( )
	{
		return this.id;
	}
	
	@Override
	public boolean equals (Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (o == this)
		{
		    return true;
		}
		if (o instanceof FinishedAssessment)
		{
			return o.hashCode( ) == this.hashCode( );
		}
		return false;
	}
	
	@Override
	public final int hashCode ( )
	{
		return this.id.hashCode( ) + this.student.hashCode( ) + this.tasks.hashCode( );
	}

    @Override
    public Set<Answer> getAnswers(Task task) 
    {
        Set<Answer> foundAnswers = new HashSet<Answer> ( );
        for (Answer answer : this.answers)
        {
            if (answer.getTask( ).getId( ).equals(task.getId( )))
            {
                foundAnswers.add(answer);
            }
        }
        return foundAnswers;
    }

    public Collection<Score> getScores ( )
    {
        return this.scores;
    }

    public Score getScores(Task task) 
    {
        for (Score score : this.scores)
        {
            if (score.getTask( ).getId( ).equals(task.getId( )))
            {
                return score;
            }
        }
        return Score.EMPTY;
    }
}
