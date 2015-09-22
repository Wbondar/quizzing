package ch.protonmail.vladyslavbond.quizzing.domain;

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
    public Member build()
    {
        Identificator<Member> id = NumericIdentificator.<Member>valueOf(get("id", Long.class));
        String screenName = get("screen_name", String.class);
        Member member = new Member (id, screenName);
        return member;
    }

}
