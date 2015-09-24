package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public final class StudentMapper
extends Object
implements Mapper<Student>
{
    private final transient MemberMapper memberMapper;

    public StudentMapper ( )
    {
        this.memberMapper = new MemberMapper ( );
    }
    
    @Override
    public void set (String l, Class<?> p, Object v)
    {
        this.memberMapper.set(l, p, v);
    }

    @Override
    public <P> P get(String label, Class<P> parameterType) throws MapperException
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
    public Class<Student> getType()
    {
        return Student.class;
    }

    @Override
    public Student build ( ) throws MapperException
    {
        Member member = this.memberMapper.build( );
        return new Student (member);
    }
}
