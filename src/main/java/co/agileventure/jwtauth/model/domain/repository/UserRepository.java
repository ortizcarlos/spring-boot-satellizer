package co.agileventure.jwtauth.model.domain.repository;

import co.agileventure.jwtauth.model.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Carlos
 */
public interface UserRepository  extends CrudRepository<User,String>{
    
    public User findByFacebook(String id);
    
    public User findByGoogle(String id);
    
    public User findByEmail(String email);
    
}
