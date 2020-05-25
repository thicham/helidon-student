/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.gestion.etudiant.mp;

import java.io.IOException;
import java.util.logging.LogManager;

import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.microprofile.server.Server;
import io.helidon.tracing.TracerBuilder;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;

/**
 * Main method simulating trigger of main method of the server.
 */
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() { }

    /**
     * Application main entry point.
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        setupLogging();

        
        Config config = buildConfig();

        // server host and port
        ServerConfiguration.Builder serverConfig = setupServerConfig(config);
        
        
        //setupTracing(config, serverConfig);
        Server server = startServer();
        System.out.println("http://localhost:" + server.port() + "/students");
    }
    
    private static Config buildConfig() {
        return Config.builder()
                .build();
    }


    /**
     * Start the server.
     * @return the created {@link Server} instance
     */
    static Server startServer() {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server.create().start();
    }
    
    private static ServerConfiguration.Builder setupServerConfig(Config config) {
        return ServerConfiguration.builder(config.get("server"));

        /*return ServerConfiguration.builder()
                .bindAddress(InetAddress.getLocalHost())
                .port(8080);
         */
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        // load logging configuration
        LogManager.getLogManager().readConfiguration(
                Main.class.getResourceAsStream("/logging.properties"));
    }
    
    /*private static void setupTracing(Config config,
            ServerConfiguration.Builder serverConfig) {
    	
    	racingConfiguration tracingConfig = new TracingConfiguration.Builder()
                .withStreaming()
                .withVerbosity()
                .withTracedAttributes(ServerRequestAttribute.CALL_ATTRIBUTES,
                     ServerRequestAttribute.HEADERS,
                     ServerRequestAttribute.METHOD_NAME)
                .build();
GrpcServerConfiguration serverConfig = GrpcServerConfiguration.builder().port(0)
                .tracer(tracer)
                .tracingConfig(tracingConfig)
                .build();
serverConfig.tracer(TracerBuilder.create("etudiant-service")
           .buildAndRegister());

//serverConfig.tracer(
//        TracerBuilder.create(config.get("tracing"))
//                .buildAndRegister());
}*/
}
