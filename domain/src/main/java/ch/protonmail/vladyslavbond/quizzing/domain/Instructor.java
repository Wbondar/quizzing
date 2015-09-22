package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Instructor 
implements Party, Identifiable<Instructor>
{
	public static final Instructor EMPTY = new Instructor ( );
	
    private Identificator<Instructor> id;
    private String screenName;
	
	private Instructor ( )
	{
		this(NumericIdentificator.<Instructor>valueOf(((NumericIdentificator<Member>)Member.EMPTY.getId( )).longValue( )), Member.EMPTY.getScreenName( ));
	}
	
	Instructor (Identificator<Instructor> id, String screenName)
	{
		this.id = id;
		this.screenName = screenName;
	}
	
	Instructor (Member member)
	{
		this(NumericIdentificator.<Instructor>valueOf(((NumericIdentificator<Member>)member.getId( )).longValue( )), member.getScreenName( ));
	}

    @Override
    public Identificator<Instructor> getId()
    {
        return this.id;
    }

    @Override
    public String getScreenName()
    {
        return this.screenName;
    }
}
