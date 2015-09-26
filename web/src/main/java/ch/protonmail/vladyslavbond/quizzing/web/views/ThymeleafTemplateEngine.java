package ch.protonmail.vladyslavbond.quizzing.web.views;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

enum ThymeleafTemplateEngine
{
    INSTANCE;
    
    private final TemplateEngine templateEngine;
    
    private ThymeleafTemplateEngine ( ) 
    {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        // XHTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode("XHTML");
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
        templateResolver.setCacheTTLMs(3600000L);
        
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }
    
    public static TemplateEngine getInstance ( )
    {
        return INSTANCE.templateEngine;
    }
}
