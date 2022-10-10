package digitalcity.demeyert.overstockmanager.controller;

import digitalcity.demeyert.overstockmanager.model.dto.UserDTO;
import digitalcity.demeyert.overstockmanager.model.forms.UserModifyForm;
import digitalcity.demeyert.overstockmanager.model.forms.UsersCreateForm;
import digitalcity.demeyert.overstockmanager.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id:[0-9]+}")
    public UserDTO getOne (@PathVariable long id) {
        return userService.getOne(id);
    }

    @PostMapping("/create")
    public UserDTO create (@RequestBody UsersCreateForm usersCreateForm) {
        return userService.create(usersCreateForm);
    }

    @PatchMapping(value = "/update")
    public String modifyUser(@RequestBody UserModifyForm userModifyForm) {
        userService.modifyImage(userModifyForm);
        return "your information is successfully updated";
    }

}
