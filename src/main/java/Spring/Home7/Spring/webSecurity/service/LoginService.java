package Spring.Home7.Spring.webSecurity.service;

import Spring.Home7.Spring.user.UserDto;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
        Map<String, String> userMap = new HashMap<>((Map.of("Alex","Password","admin","admin")));

        public Optional<Integer> login(UserDto userDto){
            String password = userMap.get(userDto.getName());
            if(password != null && password.equals(userDto.getPassword())){
                return Optional.of(1);
            }
            return Optional.empty();
        }

}
