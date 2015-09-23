package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class Student 
implements Party, Identifiable<Student>
{
	public final static Student EMPTY = new Student ( );
	
    private final String screenName;
    private final Identificator<Student> id;
	
	private Student ( )
	{
		this.id         = NumericIdentificator.<Student>valueOf(((NumericIdentificator<Member>)Member.EMPTY.getId( )).longValue( ));
		this.screenName = Member.EMPTY.getScreenName( );
	}
	
	Student (Member member)
	{
	    this.id = NumericIdentificator.<Student>valueOf(((NumericIdentificator<Member>)member.getId( )).longValue( ));
		this.screenName = member.getScreenName( );
	}
	
	public final Set<FinishedAssessment> getAssessments ( )
	{
		return Factories.<FinishedAssessmentFactory>getInstance(FinishedAssessmentFactory.class).getInstances(this.id);
	}

    @Override
    public String getScreenName()
    {
        return this.screenName;
    }

    @Override
    public Identificator<Student> getId()
    {
        return this.id;
    }
}
