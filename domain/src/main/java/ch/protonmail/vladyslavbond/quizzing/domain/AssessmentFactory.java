package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public final class AssessmentFactory 
extends SimpleFactory<Assessment>
implements Factory<Assessment> 
{
	AssessmentFactory ( ) {}
	
	public OngoingAssessment newInstance (Student student, Exam exam)
	{
		// TODO
		return new OngoingAssessment(new Assessment (new IntIdentificator<Assessment> (0), student, exam.getTasks( )));
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
