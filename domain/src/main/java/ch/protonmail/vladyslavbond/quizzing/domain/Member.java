package ch.protonmail.vladyslavbond.quizzing.domain;

public class Member 
implements Identifiable<Member>
{
	public static final Member EMPTY = new Member ( );
	
	private Member ( )
	{
		this.username = new Username("noname");
	}
	
	Member (Identificator<Member> username)
	{
		this.username = username;
	}
	
	private final Identificator<Member> username;
	
	public Identificator<Member> getId ( )
	{
		return this.username;
	}
}
