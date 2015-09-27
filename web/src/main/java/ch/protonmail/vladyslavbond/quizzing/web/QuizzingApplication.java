package ch.protonmail.vladyslavbond.quizzing.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

import ch.protonmail.vladyslavbond.quizzing.domain.Factories;
import ch.protonmail.vladyslavbond.quizzing.domain.Instructor;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactoryException;
import ch.protonmail.vladyslavbond.quizzing.domain.Member;
import ch.protonmail.vladyslavbond.quizzing.domain.MemberFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.MemberFactoryException;
import ch.protonmail.vladyslavbond.quizzing.domain.Pool;
import ch.protonmail.vladyslavbond.quizzing.domain.PoolFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.PoolFactoryException;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

@ApplicationPath("/")
public final class QuizzingApplication 
extends Application
{
    
    static
    {
        // TODO Delete placeholders.
        String singletone = "singletone" + (new Random ( )).nextInt( );
        Member member = null;
        try
        {
            member = Factories.<MemberFactory>getInstance(MemberFactory.class).newInstance(singletone, singletone);
        } catch (MemberFactoryException e)
        {
            throw new AssertionError ("Failed to instantiate placeholder member.", e);
        }
        Instructor instructor = null;
        try
        {
            instructor = Factories.<InstructorFactory>getInstance(InstructorFactory.class).getInstance(NumericIdentificator.<Instructor>valueOf(member.getId( ).toNumber( ).intValue( )));
        } catch (InstructorFactoryException e1)
        {
            throw new AssertionError ("Failed to instantiate placeholder instructor.", e1);
        }
        Pool pool = null;
        try
        {
            pool = Factories.<PoolFactory>getInstance(PoolFactory.class).newInstance(instructor, singletone);
        } catch (PoolFactoryException e)
        {
            throw new AssertionError ("Failed to instantiate placeholder pool.", e);
        }
        INSTRUCTOR = instructor;
        POOL = pool;
    }
    
    public final static Instructor INSTRUCTOR;
    public final static Pool       POOL      ;

    private final Set<Class<?>> classes;
    private final Set<Object>   singletons;
    
    public QuizzingApplication ( )
    {
        Set<Class<?>> classes = new HashSet<Class<?>> ( );
        classes.add(TasksResource.class);
        classes.add(OptionsResource.class);
        classes.add(AssessmentsResource.class);
        this.classes = classes;
        this.singletons = Collections.<Object>emptySet( );
    }
    
    @Override
    public final Set<Class<?>> getClasses ( )
    {
        return classes;
    }
    
    @Override
    public final Set<Object> getSingletons ( )
    {
        return singletons;
    }
}
