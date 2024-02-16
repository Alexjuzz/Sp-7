package Spring.Home7.Spring.webSecurity.controller;

import Spring.Home7.Spring.user.UserDto;
import Spring.Home7.Spring.webSecurity.service.JwtTokenService;
import Spring.Home7.Spring.webSecurity.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    private final JwtTokenService tokenService;
    private final LoginService loginService;

    public AuthController(JwtTokenService tokenService, LoginService loginService) {
        this.tokenService = tokenService;
        this.loginService = loginService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
            return loginService.login(userDto).map(userId -> {
                String token = tokenService.generateToken(userId, "USER");
                return new ResponseEntity<>("Bearer" + token, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
