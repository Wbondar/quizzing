package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class MemberMapper
extends NativeMapper<Member>
{
    public MemberMapper ( )
    {
        super(Member.class);
    }
    
    @Override
    public Member build() throws MemberMapperException
    {
        try
        {
            Identificator<Member> id = NumericIdentificator.<Member>valueOf(get("id", Integer.class));
            String screenName = get("screen_name", String.class);
            Member member = new Member (id, screenName);
            return member;
        } catch (MapperException e) {
            throw new MemberMapperException (e);
        }
    }

}
