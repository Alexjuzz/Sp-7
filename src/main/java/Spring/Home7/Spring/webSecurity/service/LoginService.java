package Spring.Home7.Spring.webSecurity.service;

import Spring.Home7.Spring.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
    Map<String, String> userMap = new HashMap<>(Map.of("user", "password", "admin", "admin"));

    public Optional<Integer> login(UserDto userDTO) {
        String password = userMap.get(userDTO.getName());

        if (password != null && password.equals(userDTO.getPassword())){
            return Optional.of(1);
        }

        return Optional.empty();
    }
}
