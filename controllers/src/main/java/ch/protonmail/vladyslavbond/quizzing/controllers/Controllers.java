package ch.protonmail.vladyslavbond.quizzing.controllers;

public final class Controllers
{
    private Controllers ( ) {}
    
    public static <T extends Controller> T getInstance (Class<T> typeOfController)
    {
        // TODO
        try
        {
            return typeOfController.newInstance( );
        } catch (InstantiationException e)
        {
            throw new ControllerFactoryException ("Failed to instantiate controller.", e);
        } catch (IllegalAccessException e)
        {
            throw new ControllerFactoryException ("Failed to instantiate controller.", e);
        }
    }
}
