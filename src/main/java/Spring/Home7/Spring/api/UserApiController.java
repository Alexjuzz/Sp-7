package Spring.Home7.Spring.api;

import Spring.Home7.Spring.user.User;
import Spring.Home7.Spring.user.UserDto;
import Spring.Home7.Spring.webSecurity.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserApiController {

    private final UserApiService service;

    @Autowired
    public UserApiController(UserApiService service) {
        this.service = service;
    }

    @PostMapping(value = "/")
    public UserDto createUser(@RequestBody UserDto user) {
        return service.createUser(user);
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping(value = "/all")
    public List<User> getAllUser(){
        return service.getAllUsers();
    }
    @PutMapping(value = "/{id}")
    public UserDto updateUser(@PathVariable Long id,@RequestBody UserDto user){
        return service.updateUser(id,user);
    }
    //LOGIN
    @GetMapping(value = "/login")
    public String showLoginForm(Model model){
        model.addAttribute("loginForm", new LoginDTO());
        return "Login";
    }
    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginForm") LoginDTO loginDTO) {

        return "redirect:/home";
    }

}
