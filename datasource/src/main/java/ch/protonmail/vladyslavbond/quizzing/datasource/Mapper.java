package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T> 
{
    private final           Class<T>              type;
    private       transient Map<String, Class<?>> labelToType     = new HashMap<String, Class<?>> ( );
    private       transient Map<String, Object>   labelToArgument = new HashMap<String, Object> ( );

    protected Mapper (Class<T> type)
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
    
    /**
     * DataAccess class uses this method to provide necessary values 
     * for building entities.
     * @param label label of a relational database column from which value was retrieved
     * @param parameterType type of Java class that can be mapped to SQL types
     * @param argument value stored in a column
     */
    
    public final void set (String label, Class<?> parameterType, Object argument)
    {
        if (argument == null)
        {
            remove(label);
        }
        this.labelToType.put(label, parameterType);
        this.labelToArgument.put(label, argument);
    }
    
    /**
     * Use this method to retrieve values previously provided by DataAccess class
     * for building entities
     * @param label label of a relational database column from which value was retrieved
     * @param parameterType type of Java class that can be mapped to SQL types
     * @return value which was previously stored in a column
     */
    
    public final <P> P get (String label, Class<P> parameterType)
    {
        return parameterType.cast(this.labelToArgument.get(label));
    }
    
    public final void remove(String label)
    {
        this.labelToType.remove(label);
        this.labelToArgument.remove(label);
    }
    
    /**
     * Use the method each time when finished building entity
     * so that given instance of the class could be reused
     * for building another entity
     * without older entries interfering.
     */
    
    public final void clear ( )
    {
        this.labelToArgument.clear( );
        this.labelToType.clear( );
    }

    public abstract T build();
}
