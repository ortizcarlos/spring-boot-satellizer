package co.agileventure.jwtauth.web.controller;


import co.agileventure.jwtauth.model.domain.User;
import co.agileventure.jwtauth.model.service.UserService;
import co.agileventure.jwtauth.support.AuthUtils;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
public class UserProfileController {

    @Autowired
    private UserService userService;

    @RequestMapping("/profile")
    public ResponseEntity findUser(@Context final HttpServletRequest request)
            throws JOSEException {
        User foundUser = null;
        try {
            foundUser = getAuthUser(request);
            if (foundUser == null) {
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(foundUser, HttpStatus.CREATED);
    }

    private User getAuthUser(HttpServletRequest request) throws JOSEException, ParseException {
        String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
        return userService.findOne(subject);
    }
}
