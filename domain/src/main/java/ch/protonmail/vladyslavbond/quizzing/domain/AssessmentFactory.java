package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class AssessmentFactory 
extends SimpleFactory<Assessment>
implements Factory<Assessment> 
{
    AssessmentFactory ( ) 
    {
        super(Assessment.class, new AssessmentMapper ( ));
    }
	
	public OngoingAssessment newInstance (Student student, Exam exam)
	{
		// TODO
		return new OngoingAssessment(new Assessment (NumericIdentificator.<Assessment>valueOf(0), student, exam.getTasks( )));
	}
	
	@Override
	public Assessment getInstance (Identificator<Assessment> id) 
	{
		// TODO
		return Assessment.EMPTY;
	}
	
	public Set<Assessment> getInstances (Identificator<Member> idOfStudent)
	{
		// TODO
		return java.util.Collections.<Assessment>emptySet( );
	}
	
	public boolean destroy (Assessment assessment)
	{
		// TODO
		return false;
	}
	
	public boolean destroy (OngoingAssessment assessment)
	{
		// TODO
		return false;
	}
}
