package ch.protonmail.vladyslavbond.quizzing.domain;

public final class MemberFactory 
implements Factory<Member> 
{
	MemberFactory ( ) {}
	
	@Override
	public Member getInstance (Identificator<Member> id) 
	{
		// TODO
		return new Member(id);
	}
	
	public Student getStudent (Member member)
	{
		// TODO
		return new Student (member);
	}
	
	public Instructor getInstructor (Member member)
	{
		// TODO
		return new Instructor (member);
	}
}
