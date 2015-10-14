/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.web.controller;

import co.agileventure.jwtauth.model.domain.User;
import co.agileventure.jwtauth.model.service.UserService;
import co.agileventure.jwtauth.support.AuthUtils;
import co.agileventure.jwtauth.support.Token;
import static co.agileventure.jwtauth.web.controller.AuthenticationConstants.CONFLICT_MSG;
import static co.agileventure.jwtauth.web.controller.AuthenticationConstants.NOT_FOUND_MSG;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos
 */
@Component
public class UserProcessorImpl implements UserProcessor {

    @Autowired
    private UserService userService;  
  
    public ResponseEntity processUser(final HttpServletRequest request, final User.Provider provider,
            final String id, final String displayName, final String email, final String picture,
            final String name, final String givenName, final String familyName)
            throws JOSEException, ParseException {

        User user = null;
        switch (provider) {
            case FACEBOOK:
                user = userService.findByFacebook(id);
                break;
            case GOOGLE:
                user = userService.findByGoogle(id);
                break;
            default:
                return new ResponseEntity<String>("Unknown OAUTH2.0 Provider", HttpStatus.NOT_FOUND);
        }

        //If not found by provider try to find it by email
        if (user == null && StringUtils.isNotEmpty(email)) {
            user = userService.findByEmail(email);
        }

        // Step 3a. If user is already signed in then link accounts.
        User userToSave;
        final String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        if (StringUtils.isNotBlank(authHeader)) {
            if (user == null) {
                return new ResponseEntity<String>(String.format(CONFLICT_MSG, provider.capitalize()),
                        HttpStatus.CONFLICT);
            }
            final String subject = AuthUtils.getSubject(authHeader);
            final User foundUser = userService.findOne(subject);
            if (foundUser == null) {
                return new ResponseEntity<String>(NOT_FOUND_MSG, HttpStatus.NOT_FOUND);
            }

            userToSave = foundUser;
            boolean updated = setUserProvider(provider, userToSave, id);
            if (userToSave.getDisplayName() == null) {
                userToSave.setDisplayName(displayName);
                updated = true;
            }
            if (userToSave.getPicture() == null) {
                userToSave.setPicture(picture);
                updated = true;
            }

            if (updated) {
                userToSave = userService.save(userToSave);
            }
        } else {
            // Step 3b. Create a new user account or return an existing one.
            if (user != null) {
                userToSave = user;
                if (setUserProvider(provider, userToSave, id)) {
                    if (userToSave.getPicture() == null) {
                        userToSave.setPicture(picture);
                    }
                    userToSave = userService.save(userToSave);
                }
            } else {
                userToSave = new User();
                userToSave.setId(UUID.randomUUID().toString());
                userToSave.setDisplayName(displayName);
                userToSave.setEmail(email);
                userToSave.setName(name);
                userToSave.setGivenName(givenName);
                userToSave.setPicture(picture);
                userToSave.setFamilyName(familyName);

                setUserProvider(provider, userToSave, id);
                userToSave = userService.save(userToSave);
            }
        }

        Token token = AuthUtils.createToken(request.getRemoteHost(), userToSave.getId());
        return new ResponseEntity<Token>(token, HttpStatus.OK);
    }

    private boolean setUserProvider(User.Provider provider, User userToSave, String id) {
        boolean updated = false;
        switch (provider) {
            case FACEBOOK:
                if (userToSave.getFacebook() == null) {
                    userToSave.setFacebook(id);
                    updated = true;
                }
                break;
            case GOOGLE:
                if (userToSave.getGoogle() == null) {
                    userToSave.setGoogle(id);
                    updated = true;
                }
                break;
        }
        return updated;
    }
}
