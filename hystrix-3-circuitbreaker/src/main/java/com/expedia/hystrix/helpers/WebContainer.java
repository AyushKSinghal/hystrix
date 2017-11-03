package com.expedia.hystrix.helpers;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ServletInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import javax.servlet.Servlet;

public class WebContainer {

    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    private final UndertowJaxrsServer server;

    public WebContainer() {
        this.server = new UndertowJaxrsServer();
    }

    public void deployServlet(String contextPath, String servletName, Class<? extends Servlet> servletClass) {
        DeploymentInfo servletBuilder = Servlets
                .deployment()
                .setClassLoader(this.getClass().getClassLoader())
                .setContextPath(contextPath)
                .setDeploymentName(servletName);

        ServletInfo servletInfo = Servlets.servlet(servletName, servletClass);
        servletInfo.addMapping("/");
        servletBuilder.addServlet(servletInfo);

        server.deploy(servletBuilder);
    }

    public void start() {
        Undertow.Builder builder = Undertow.builder().addHttpListener(PORT, HOST);
        server.start(builder);
    }

    public void stop() {
        server.stop();
    }
}
