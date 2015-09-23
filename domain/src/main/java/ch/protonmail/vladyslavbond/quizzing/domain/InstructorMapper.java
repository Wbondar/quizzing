package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;

public final class InstructorMapper
extends Object
implements Mapper<Instructor>
{
    private final transient MemberMapper memberMapper;

    public InstructorMapper ( )
    {
        this.memberMapper = new MemberMapper ( );
    }
    
    @Override
    public void set (String l, Class<?> p, Object v)
    {
        this.memberMapper.set(l, p, v);
    }

    @Override
    public <P> P get(String label, Class<P> parameterType)
    {
        return this.memberMapper.<P>get(label, parameterType);
    }

    @Override
    public void clear()
    {
        this.memberMapper.clear( );
    }

    @Override
    public void remove(String label)
    {
        this.memberMapper.remove(label);
    }

    @Override
    public Class<Instructor> getType()
    {
        return Instructor.class;
    }

    @Override
    public Instructor build ( )
    {
        Member member = this.memberMapper.build( );
        return new Instructor (member);
    }
}
