/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.web.controller;

import co.agileventure.jwtauth.model.domain.User;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Carlos
 */
public interface UserProcessor {
    ResponseEntity processUser(final HttpServletRequest request, final User.Provider provider,
            final String id, final String displayName, final String email, final String picture,
            final String name, final String givenName, final String familyName)
            throws JOSEException, ParseException;
}
