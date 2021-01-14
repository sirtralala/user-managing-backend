package at.technikumwien;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/resources/user")
@CrossOrigin
@Log
public class UserResource {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        log.info("create() >> person    =" + user);
        
        user.setId(null);   // better safe than sorry
        user = userRepository.save(user);
        
        URI location = WebMvcLinkBuilder.linkTo(
			WebMvcLinkBuilder.methodOn(getClass()).retrieve(user.getId())
		).toUri();
		
        return ResponseEntity.created(location).build();
	}
    
    @GetMapping("/{id}")
    public User retrieve(@PathVariable long id) {
        log.info("retrieve() >> id=" + id);
        
        return userRepository.findById(id)
        	.orElseThrow(() -> new EmptyResultDataAccessException("can't find person with id " + id, 1));
    }

    @PutMapping("/{id}")
    public void update(
    	@PathVariable long id,
    	@RequestBody User user
    ) {
        log.info("update() >> id=" + id + ", user=" + user);
        
        user.setId(id); // better safe than sorry
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.info("delete() >> id=" + id);
        
        userRepository.deleteById(id);   // throws EmptyResultDataAccessException if user could not be found
    }

    @GetMapping
    public List<User> retrieveAll() {
        log.info("retrieveAll()");
        
        return userRepository.findAll();
    }
}