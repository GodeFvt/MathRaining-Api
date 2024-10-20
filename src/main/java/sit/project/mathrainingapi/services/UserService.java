package sit.project.mathrainingapi.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.project.mathrainingapi.dtos.UserResponseDTO;
import sit.project.mathrainingapi.entities.User;
import sit.project.mathrainingapi.exceptions.NotFoundException;
import sit.project.mathrainingapi.repositories.UserRepository;
import sit.project.mathrainingapi.utils.ListMapper;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper mapper;

    public List<UserResponseDTO> getAllUser() {
        return listMapper.mapList(userRepository.findAll(), UserResponseDTO.class);
    }

    public UserResponseDTO getUserById(String id) {
        return mapper.map(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")), UserResponseDTO.class);
    }

    public UserResponseDTO createUser(User newUser) {
        return mapper.map(userRepository.save(newUser), UserResponseDTO.class);
    }

    public UserResponseDTO updateUser(String id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        if (user.getUserName() != null) {
            user.setUserName(newUser.getUserName());
        }
        if (user.getPassword() != null) {
            user.setPassword(newUser.getPassword());
        }
        if (user.getRole() != null) {
            user.setRole(newUser.getRole());
        }
        if (user.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        return mapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

}
