package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public final class Student 
extends Member 
{
	public final static Student EMPTY = new Student ( );
	
	private Student ( )
	{
		super(Member.EMPTY.getId( ));
		this.member = Member.EMPTY;
	}
	
	private final Member member;
	
	Student (Member member)
	{
		super(member.getId( ));
		this.member = member;
	}
	
	public final Set<Assessment> getAssessments ( )
	{
		return Factories.<AssessmentFactory>getInstance(AssessmentFactory.class).getInstances(this.member.getId( ));
	}
}
