package com.onblocktrust.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @author Kamil Khan
 */

@Slf4j
@Path("/")
@ApplicationScoped
public class DefaultController {

    private final Template index;

    @ConfigProperty(name = "app.id")
    String appId;

    @ConfigProperty(name = "app.name")
    String appName;

    @ConfigProperty(name = "app.gitTag")
    String appGitTag;

    public DefaultController(Template index) {
        this.index = requireNonNull(index, "index template is required");
    }

    @GET
    @Path("index")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        String appMode = io.quarkus.runtime.LaunchMode.current().name();
        return index.data("appName", this.appName)
                .data("appMode", appMode)
                .data("appVersion", this.appGitTag)
                .data("appModeIsDev", appMode.equals("DEVELOPMENT"));
    }

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Ping Pong!";
    }

    @GET
    @Path("app-info")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> appInfo() {
        return Map.of(
                "mode", io.quarkus.runtime.LaunchMode.current(),
                "id", this.appId,
                "name", this.appName,
                "gitTag", this.appGitTag
        );
    }

}