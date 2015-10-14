/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.web.controller.helper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.ws.rs.core.Response;

/**
 *
 * @author Carlos
 */
public class JsonHelper {
    
    private final ObjectMapper MAPPER = new ObjectMapper();

  
     /*
     * Helper methods
     */
    public Map<String, Object> getResponseEntity(final Response response) throws JsonParseException,
            JsonMappingException, IOException {
        return MAPPER.readValue(response.readEntity(String.class),
                new TypeReference<Map<String, Object>>() {
                });
    }

    public Map<String, Object> getJsonMap(final String response) throws JsonParseException,
            JsonMappingException, IOException {
        return MAPPER.readValue(response,
                new TypeReference<Map<String, Object>>() {
                });
    }
}
