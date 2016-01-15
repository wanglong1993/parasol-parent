package com.ginkgocap.parasol.oauth2.web.jetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * After you launch the app, you can seek a bearer token like this:
 *
 * <pre>
 * curl localhost:8081/oauth/token -d "grant_type=password&scope=getIdentifyingCode&username=13677687632&password=123456" -u "7647448850:88aec17e1857ca1341ee6b16b0b75aef"
 * </pre>
 *
 * <ul>
 * <li>grant_type=password (user credentials will be supplied)</li>
 * <li>scope=read (read only scope)</li>
 * <li>username=greg (username checked against user details service)</li>
 * <li>password=turnquist (password checked against user details service)</li>
 * <li>-u foo:bar (clientid:secret)</li>
 * </ul>
 *
 * Response should be similar to this:
 * <code>{"access_token":"310f25d3-978b-4f6a-9ffd-5161e54dffe4","token_type":"bearer","refresh_token":"31cfc35a-8975-4781-a9eb-8c989886fc48","expires_in":43152,"scope":"getIdentifyingCode"</code>
 *
 * With the token value, you can now interrogate the RESTful interface like
 * this:
 *
 * <pre>
 * curl -H "Authorization: bearer [access_token]" localhost:8080/flights/1
 * </pre>
 *
 * You should then see the pre-loaded data like this:
 *
 * <pre>
 * {
 *      "origin" : "Nashville",
 *      "destination" : "Dallas",
 *      "airline" : "Spring Ways",
 *      "flightNumber" : "OAUTH2",
 *      "date" : null,
 *      "traveler" : "Greg Turnquist",
 *      "_links" : {
 *          "self" : {
 *              "href" : "http://localhost:8080/flights/1"
 *          }
 *      }
 * }
 * </pre>
 *
 * Test creating a new entry:
 *
 * <pre>
 * curl -i -H "Authorization: bearer [access token]" -H "Content-Type:application/json" localhost:8080/flights -X POST -d @flight.json
 * </pre>
 *
 * Insufficient scope? (read not write) Ask for a new token!
 *
 * <pre>
 * curl localhost:8080/oauth/token -d "grant_type=password&scope=write&username=greg&password=turnquist" -u foo:bar
 * 
 * {"access_token":"cfa69736-e2aa-4ae7-abbb-3085acda560e","token_type":"bearer","expires_in":43200,"scope":"write"}
 * </pre>
 *
 * Retry with the new token. There should be a Location header.
 *
 * <pre>
 * Location: http://localhost:8080/flights/2
 * 
 * curl -H "Authorization: bearer [access token]" localhost:8080/flights/2
 * </pre>
 *
 * @author Craig Walls
 * @author Greg Turnquist
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// @EnableAuthorizationServer
// @EnableResourceServer
public class ParasolOauth2Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(new Object[] { "classpath:/applicationContext.xml" }, args);
		// SpringApplication application = new
		// SpringApplication(SampleJettyApplication.class);
		// application.setShowBanner(false);
		// application.run(args);
	}

}
