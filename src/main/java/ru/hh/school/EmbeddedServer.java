package ru.hh.school;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.nio.file.Paths;

public class EmbeddedServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(9999);

        HandlerCollection handlers = new HandlerCollection();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(Paths.get("src", "main", "webapp", "frontend").toString());

        WebAppContext context = new WebAppContext();
        context.setResourceBase(Paths.get("src", "main", "webapp").toString());
        context.setDescriptor(Paths.get("target", "web.xml").toString());
        context.setContextPath("/frontend");

        handlers.addHandler(context);
        handlers.addHandler(resourceHandler);

        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
