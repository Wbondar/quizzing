package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.postgresql.Driver;

enum NativeConnectionFactory 
implements ConnectionFactory
{
    INSTANCE ( );
    
    private String url;
    private String user;
    private String password;

    NativeConnectionFactory ( )
    {
        this(ResourceBundle.getBundle("Database"));
    }
    
    NativeConnectionFactory (ResourceBundle b)
    {
        this(b.getString("URL"), b.getString("USER"), b.getString("PASSWORD"));
    }
    
    NativeConnectionFactory (String url, String user, String password)
    {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection ( ) 
            throws SQLException
    {
        if (!Driver.isRegistered())
            throw new SQLException ("Database driver is not registered.");
        try
        {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e)
        {
           throw new SQLException ("Failed to connect to the database.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
