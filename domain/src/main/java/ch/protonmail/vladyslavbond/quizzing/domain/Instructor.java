package ch.protonmail.vladyslavbond.quizzing.domain;

public final class Instructor 
extends Member 
{
	public static final Instructor EMPTY = new Instructor ( );
	
	private Instructor ( )
	{
		super(Member.EMPTY.getId( ));
	}
	
	Instructor (Identificator<Member> id)
	{
		super(id);
	}
	
	Instructor (Member member)
	{
		this(member.getId( ));
	}
}
