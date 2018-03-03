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

        WebAppContext context = new WebAppContext();
        context.setResourceBase(EmbeddedServer.class.getResource("/webapp").toExternalForm());
        context.setDescriptor(Paths.get("target", "web.xml").toString());
        context.setContextPath("/");

        handlers.addHandler(context);

        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
