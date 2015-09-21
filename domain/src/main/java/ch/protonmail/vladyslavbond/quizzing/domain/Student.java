package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public final class Student 
extends Member 
{
	public final static Student EMPTY = new Student ( );
	
	private Student ( )
	{
		super(Member.EMPTY.getId( ), Member.EMPTY.getScreenName( ));
	}
	
	Student (Member member)
	{
		super(member.getId( ), member.getScreenName( ));
	}
	
	public final Set<Assessment> getAssessments ( )
	{
		return Factories.<AssessmentFactory>getInstance(AssessmentFactory.class).getInstances(super.getId( ));
	}
}
