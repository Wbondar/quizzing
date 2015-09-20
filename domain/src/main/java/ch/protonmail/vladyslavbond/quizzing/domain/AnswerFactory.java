package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collection;
import java.util.Set;

public final class AnswerFactory 
extends SimpleFactory<Answer>
implements Factory<Answer> 
{
	AnswerFactory ( ) {}
	
	public Answer newInstance (Assessment assessment, Task task, Collection<String> input)
	{
		// TODO
		Identificator<Answer> id = new IntIdentificator<Answer>(Integer.valueOf(assessment.getId( ).toString( ).concat(task.getId( ).toString( ))));
		return new Answer(id, task, assessment.getStudent( ), input);
	}
	
	public ScoredAnswer newInstance (OngoingAssessment assessment, Task task, int reward)
	{
		// TODO
		Identificator<Answer> id = new IntIdentificator<Answer>(Integer.valueOf(assessment.getId( ).toString( ).concat(task.getId( ).toString( ))));
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
