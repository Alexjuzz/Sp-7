package Spring.Home7.Spring.api;

import Spring.Home7.Spring.exceptions.EntityNotFoundException;
import Spring.Home7.Spring.repositories.UserRepository;
import Spring.Home7.Spring.user.User;
import Spring.Home7.Spring.user.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApiService {

    private final UserRepository userRepository;

    @Autowired
    public UserApiService(UserRepository repository){
        this.userRepository = repository;
    }

    public  UserDto createUser(@NotNull UserDto userDto){
        User user = convertEntity(userDto);
        userRepository.save(user);
        return convertDto(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
    public UserDto updateUser(Long id, @NotNull UserDto userDto){
        User user = getUserById(id);
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return convertDto(user);
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }


    //region private methods
    private UserDto convertDto(@NotNull User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }

    private @NotNull User convertEntity(UserDto userDto ){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        return user;
    }
    //endregion
}
