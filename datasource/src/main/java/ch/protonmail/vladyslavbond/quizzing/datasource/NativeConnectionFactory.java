package ch.protonmail.vladyslavbond.quizzing.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
        return DriverManager.getConnection(url, user, password);
    }
}
