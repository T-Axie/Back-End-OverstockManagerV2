package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.mapper.UsersMapper;
import digitalcity.demeyert.overstockmanager.model.dto.UserDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Users;
import digitalcity.demeyert.overstockmanager.model.forms.UserModifyForm;
import digitalcity.demeyert.overstockmanager.model.forms.UsersCreateForm;
import digitalcity.demeyert.overstockmanager.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class UserService {

    UserRepository userRepository;
    UsersMapper mapper;

    public UserService(UserRepository userRepository, UsersMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserDTO getOne(long id/*, UsernamePasswordAuthenticationToken token*/) {
//        Users users = userRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(Card.class, id));
//        String email = token.getPrincipal().toString();

        return mapper.fromEntity(userRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(Card.class, id)));
    }

    public void update(UserModifyForm userDTO) {
        Users usersToUpdate = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ElementNotFoundException(Card.class, userDTO.getId()));

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
