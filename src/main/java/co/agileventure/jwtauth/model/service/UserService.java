package co.agileventure.jwtauth.model.service;

import co.agileventure.jwtauth.model.domain.User;

/**
 *
 * @author Carlos
 */
public interface UserService {
    User findByFacebook(String id);
    User findByGoogle(String id);
    User findByEmail(String email);
    User findOne(String subject);
    User save(User user);
}
