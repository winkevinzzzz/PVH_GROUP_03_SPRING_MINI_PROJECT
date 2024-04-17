package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.model.CustomUserDetail;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.repository.UserRepository;
import org.example.spring_boot_mini_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper=new ModelMapper();
    //private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
       /// this.encoder = encoder;
    }


    @Override
    public User createUser(AppUserRequest appUserRequest) {
        String email = String.valueOf(userRepository.findByEmail(appUserRequest.getEmail()));
        if(appUserRequest.getEmail().equalsIgnoreCase(email))
        {

        }
        User userId =userRepository.insert(appUserRequest);
        // set password

        appUserRequest.setPassword(appUserRequest.getPassword());
        User user =userRepository.findById(userId.getUserId());
        System.out.println(userId.getUserId());
        return modelMapper.map(user,User.class);

    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

//        if(user == null){
//            throw new NotFoundException("could not found user..!!");
//        }
        return new CustomUserDetail(user);

    }
}
