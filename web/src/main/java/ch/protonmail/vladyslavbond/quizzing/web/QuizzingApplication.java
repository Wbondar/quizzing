package ch.protonmail.vladyslavbond.quizzing.web;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public final class QuizzingApplication 
extends Application
{
    private final static Set<Class<?>> classes = new HashSet<Class<?>> ( );
    private final static Set<Object>   singletons = new HashSet<Object> ( );
    
    static
    {
        classes.add(TasksResource.class);
        classes.add(OptionsResource.class);
        classes.add(AssessmentsResource.class);
        singletons.add(TasksResource.INSTANCE);
        singletons.add(OptionsResource.INSTANCE);
        singletons.add(AssessmentsResource.INSTANCE);
    }
    
    public QuizzingApplication ( )
    {
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
