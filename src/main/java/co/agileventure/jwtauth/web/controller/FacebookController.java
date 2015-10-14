/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.web.controller;

import co.agileventure.jwtauth.model.domain.User;
import static co.agileventure.jwtauth.web.controller.OAuthConstants.CLIENT_ID_KEY;
import static co.agileventure.jwtauth.web.controller.OAuthConstants.CLIENT_SECRET;
import static co.agileventure.jwtauth.web.controller.OAuthConstants.CODE_KEY;
import static co.agileventure.jwtauth.web.controller.OAuthConstants.REDIRECT_URI_KEY;
import co.agileventure.jwtauth.web.controller.helper.JsonHelper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jersey.repackaged.com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
public class FacebookController {    
    private static Logger LOG = Logger.getLogger(FacebookController.class);
    
    private Client restClient;
    
    @Autowired
    private UserProcessor userProcessor;
    
    @Value("${facebook.secret}")
    private String facebookSecret;
    
    private JsonHelper jsonHelper = new JsonHelper();

    public FacebookController() {        
        restClient = ClientBuilder.newClient();
    }  

    @RequestMapping("/auth/facebook")
    public ResponseEntity loginFacebook(@RequestBody final Payload payload,
            @Context final HttpServletRequest request) throws JsonParseException, JsonMappingException,
            IOException, ParseException, JOSEException {
        final String accessTokenUrl = "https://graph.facebook.com/v2.3/oauth/access_token";
        final String graphApiUrl = "https://graph.facebook.com/v2.3/me";
        final String userPictureUrl = "https://graph.facebook.com/v2.3/{user-id}/picture";

        Response response;

        // Step 1. Exchange authorization code for access token.
        response
                = restClient.target(accessTokenUrl).queryParam(CLIENT_ID_KEY, payload.getClientId())
                .queryParam(REDIRECT_URI_KEY, payload.getRedirectUri())
                .queryParam(CLIENT_SECRET, facebookSecret)
                .queryParam(CODE_KEY, payload.getCode()).request("text/plain")
                .accept(MediaType.TEXT_PLAIN).get();

        final String paramStr = Preconditions.checkNotNull(response.readEntity(String.class));

        final Map<String, Object> apiInfo = jsonHelper.getJsonMap(paramStr);

        response
                = restClient.target(graphApiUrl).queryParam("access_token", apiInfo.get("access_token").toString())
                .queryParam("expires_in", apiInfo.get("expires_in").toString()).request("text/plain").get();

        final Map<String, Object> userInfo = jsonHelper.getResponseEntity(response);

        String userId = userInfo.get("id").toString();
        String userIdPictureUrl = userPictureUrl.replace("{user-id}", userId);

        // Step 3. Process the authenticated the user.
        return userProcessor.processUser(request, User.Provider.FACEBOOK,
                userInfo.get("id").toString(),
                userInfo.get("name").toString(),
                userInfo.get("email").toString(),
                userIdPictureUrl,
                userInfo.get("name").toString(),
                userInfo.get("first_name").toString(),
                userInfo.get("last_name").toString());
    }

}
