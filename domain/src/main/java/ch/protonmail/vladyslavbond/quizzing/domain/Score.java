package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

/**
 * Represents score for a particular task, solved by a particular student
 * in a particular assessment.
 * All answers provided by the same student to the same task in the same assessment
 * correspond to a single score.
 * @author Vladyslav Bondarenko
 *
 */

public final class Score 
extends Object
implements Identifiable<Score>
{
	public final static Score EMPTY = new Score ( );

    private final Identificator<Score> id;
	private final Student              student;
	private final Task                 task;
    private final Set<Answer>          answers;
    private final int                  reward;
    
	private Score ( )
	{
		this(NumericIdentificator.<Score>valueOf(0L), Student.EMPTY, Task.EMPTY, Collections.<Answer>emptySet( ), 0);
	}
	
	Score (Identificator<Score> id, Student student, Task task, Set<Answer> answers, int reward)
	{
	    this.id      = id;
		this.student = student;
		this.task    = task;
		this.answers = answers;
		this.reward  = reward;
	}
	
	public final Student getStudent ( )
	{
	    return this.student;
	}
	
	public final Task getTask ( )
	{
	    return this.task;
	}
	
	public final Integer getReward ( )
	{
		return Integer.valueOf(this.reward);
	}
	
	public final Set<Answer> getAnswers ( )
	{
	    return this.answers;
	}
	
	public final Identificator<Score> getId ( )
	{
	    return this.id;
	}
	
	@Override
	public int hashCode ( )
	{
		return this.id.hashCode( ) + this.student.hashCode( ) + this.task.hashCode( ) + this.answers.hashCode( ) + this.reward * this.reward;
	}
}
