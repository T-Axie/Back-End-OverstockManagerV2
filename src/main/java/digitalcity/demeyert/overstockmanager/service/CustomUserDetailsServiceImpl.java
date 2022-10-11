package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.model.entity.Users;
import digitalcity.demeyert.overstockmanager.model.forms.UsersCreateForm;
import digitalcity.demeyert.overstockmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public CustomUserDetailsServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("connexion impossible"));
    }

    public void create(UsersCreateForm toCreate){
        Users users = toCreate.toEntity();
        users.setPassword( encoder.encode(users.getPassword()) );
        repository.save( users );
    }

}
