package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Member 
implements Identifiable<Member>
{
	public static final Member EMPTY = new Member ( );
    private final String screenName;
	
	private Member ( )
	{
		this.id         = NumericIdentificator.<Member>valueOf(0);
		this.screenName = "nominevacans";
	}
	
	public Member(Identificator<Member> id, String screenName)
    {
        this.id = id;
        this.screenName = screenName;
    }

    private final Identificator<Member> id;
	
	public Identificator<Member> getId ( )
	{
		return this.id;
	}
	
	public String getScreenName ( )
	{
	    return this.screenName;
	}
}
