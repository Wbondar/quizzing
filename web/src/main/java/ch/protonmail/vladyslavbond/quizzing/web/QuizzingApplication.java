package ch.protonmail.vladyslavbond.quizzing.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public final class QuizzingApplication 
extends Application
{
    private final Set<Class<?>> classes;
    private final Set<Object>   singletons;
    
    public QuizzingApplication ( )
    {
        this.classes    = Collections.<Class<?>>emptySet( );
        this.singletons = new HashSet<Object> ( );
        this.singletons.add(MyResource.INSTANCE);
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
