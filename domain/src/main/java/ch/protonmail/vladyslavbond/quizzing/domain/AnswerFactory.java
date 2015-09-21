package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class AnswerFactory 
extends SimpleFactory<Answer>
implements Factory<Answer> 
{
	AnswerFactory ( ) 
	{
	    super(Answer.class, new AnswerMapper ( ));
	}
	
	public Answer newInstance (Assessment assessment, Task task, String input)
	{
		// TODO
		Identificator<Answer> id = NumericIdentificator.<Answer>valueOf(Integer.valueOf(assessment.getId( ).toString( ).concat(task.getId( ).toString( ))));
		return new Answer(id, task, assessment.getStudent( ), input);
	}
	
	public ScoredAnswer newInstance (OngoingAssessment assessment, Task task, int reward)
	{
		// TODO
		Identificator<Answer> id = NumericIdentificator.<Answer>valueOf(Integer.valueOf(assessment.getId( ).toString( ).concat(task.getId( ).toString( ))));
		return new ScoredAnswer(this.getInstance(id), reward);
	}
	
	@Override
	public Answer getInstance (Identificator<Answer> id) 
	{
		// TODO
		return Answer.EMPTY;
	}
	
	public Set<? extends Answer> getInstances (Identificator<Assessment> idOfAssessment)
	{
		// TODO
		return java.util.Collections.<Answer>emptySet( );
	}
}
