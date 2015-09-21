package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class Instructor 
extends Member 
{
	public static final Instructor EMPTY = new Instructor ( );
	
	private Instructor ( )
	{
		super(Member.EMPTY.getId( ), Member.EMPTY.getScreenName( ));
	}
	
	Instructor (Identificator<Member> id, String screenName)
	{
		super(id, screenName);
	}
	
	Instructor (Member member)
	{
		this(member.getId( ), member.getScreenName( ));
	}
}
