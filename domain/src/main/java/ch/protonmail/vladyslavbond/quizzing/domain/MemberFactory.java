package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class MemberFactory 
extends SimpleFactory<Member>
implements Factory<Member> 
{
    MemberFactory ( ) 
    {
        super(Member.class, new MemberMapper ( ));
    }
    
    public Member newInstance (String screenName, String password)
    {
        Object[] arguments = {screenName, password};
        try
        {
            return this.getDataAccess().store("{CALL member_create (?, ?)}", arguments);
        } catch (DataAccessException e)
        {
            throw new MemberFactoryException ("Failed to create new member.", e);
        }
    }
	
	@Override
	public Member getInstance (Identificator<Member> id) 
	{
	    Object[] arguments = {((NumericIdentificator<Member>)id).longValue( )};
		try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_members WHERE id = ?;", arguments);
        } 
		catch (DataAccessException e)
        {
            throw new MemberFactoryException ("Failed to get member by identificator.", e);
        }
	}
}
