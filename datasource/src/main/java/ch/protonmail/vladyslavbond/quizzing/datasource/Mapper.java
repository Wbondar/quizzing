package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Mapper<T> 
{
    private final           Class<T>              type;
    private       transient Set<Class<?>>         parameterTypes  = new HashSet<Class<?>> ( );
    private       transient Map<String, Class<?>> labelToType     = new HashMap<String, Class<?>> ( );
    private       transient Map<String, Object>   labelToArgument = new HashMap<String, Object> ( );

    Mapper (Class<T> type)
    {
        this.type = type;
    }
    
    /**
     * All extensions must provide public constructor with no parameters.
     */
    public Mapper ( ) 
    {
        this.type = null;
    }
    
    protected final Class<T> getType ( )
    {
        return this.type;
    }
    
    public final void set (String label, Class<?> parameterType, Object argument)
    {
        if (argument == null)
        {
            remove(label);
        }
        this.parameterTypes.add(parameterType);
        this.labelToType.put(label, parameterType);
        this.labelToArgument.put(label, argument);
    }
    
    public final <P> P get (String label, Class<P> parameterType)
    {
        return parameterType.cast(this.labelToArgument.get(label));
    }
    
    public final void remove(String label)
    {
        this.labelToType.remove(label);
        this.labelToArgument.remove(label);
    }
    
    public final void clear ( )
    {
        this.labelToArgument.clear( );
        this.labelToType.clear( );
        this.parameterTypes.clear( );
    }

    public abstract T build();
}
