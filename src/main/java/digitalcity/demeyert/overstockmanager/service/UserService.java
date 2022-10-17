package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.mapper.UsersMapper;
import digitalcity.demeyert.overstockmanager.model.dto.UserDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Users;
import digitalcity.demeyert.overstockmanager.model.forms.UserModifyForm;
import digitalcity.demeyert.overstockmanager.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UsersMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UsersMapper mapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public UserDTO getOne(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new ElementNotFoundException(Card.class, email));
        user.setPassword(null);
        return mapper.fromEntity(user);
    }

    public void update(UsernamePasswordAuthenticationToken token, UserModifyForm userDTO) {
        Users usersToUpdate = userRepository.findByEmail(token.getPrincipal().toString())
                .orElseThrow(() -> new ElementNotFoundException(Card.class, token.getPrincipal().toString()));

        if (userDTO.getURLImage() != null)
            usersToUpdate.setURLImage(userDTO.getURLImage());
        if (userDTO.getDescription() != null)
            usersToUpdate.setDescription(userDTO.getDescription());
        if (userDTO.getUsername() != null)
            usersToUpdate.setUsername(userDTO.getUsername());


        userRepository.save(usersToUpdate);
    }

    public static boolean isAuthorize (String email, UsernamePasswordAuthenticationToken token) {
        if (token == null)
            throw new IllegalArgumentException();
        String username = token.getPrincipal().toString();
        return Objects.equals(email, username);
    }
}
