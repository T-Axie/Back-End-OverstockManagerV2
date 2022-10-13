package digitalcity.demeyert.overstockmanager.controller;

import digitalcity.demeyert.overstockmanager.model.dto.KeyDTO;
import digitalcity.demeyert.overstockmanager.model.dto.UserDTO;
import digitalcity.demeyert.overstockmanager.model.forms.LoginForm;
import digitalcity.demeyert.overstockmanager.model.forms.UserModifyForm;
import digitalcity.demeyert.overstockmanager.model.forms.UsersCreateForm;
import digitalcity.demeyert.overstockmanager.service.CustomUserDetailsServiceImpl;
import digitalcity.demeyert.overstockmanager.service.UserService;
import digitalcity.demeyert.overstockmanager.utils.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("http://localhost:8100")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;


    public UserController(UserService userService, CustomUserDetailsServiceImpl userDetailsService, AuthenticationManager authManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/myProfile")
    public UserDTO getOne (@PathVariable long id, UsernamePasswordAuthenticationToken token) {
        return userService.getOne(id/*, token */);
    }

    @PostMapping("/register")
    public void createUser (@RequestBody UsersCreateForm usersCreateForm) {
        userDetailsService.create(usersCreateForm);
    }

    @PostMapping("/login")
    public KeyDTO login(@Valid @RequestBody LoginForm form){
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword()));
        return jwtProvider.createToken(auth);
    }


    @PatchMapping(value = "/update")
    public String modifyUser(@RequestBody UserModifyForm userModifyForm) {
        userService.update(userModifyForm);
        return "your information is successfully updated";
    }

}
