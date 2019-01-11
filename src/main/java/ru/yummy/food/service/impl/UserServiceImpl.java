package ru.yummy.food.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.yummy.food.entity.User;
import ru.yummy.food.model.UserModel;
import ru.yummy.food.repo.UserRepository;
import ru.yummy.food.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByUsername( login );
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority( user.getRole() ));
    }

    private List<SimpleGrantedAuthority> getAuthority(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findOne(String login) {
        return userRepo.findByUsername(login);
    }

    @Override
    public User findById(int id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @Override
    public UserModel update(UserModel userModel) {
        User user = findById(userModel.getId());
        if(user != null) {
            BeanUtils.copyProperties(userModel, user, "password");
            userRepo.save(user);
        }
        return userModel;
    }

    @Override
    public User save(UserModel user) {
          String admin_ps = bcryptEncoder.encode("admin_pswd");
          String user_ps = bcryptEncoder.encode("1111");
//        User newUser = new User();
//        newUser.setUsername(user.getUsername());
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        newUser.setRole(user.getRole());
//        return userRepo.save(newUser);
        System.out.println( "PASSWORD FOR admin_pswd is: "+admin_ps );
        System.out.println( "PASSWORD FOR user_ps is: "+user_ps );
        return null;
    }

//    @PostConstruct
//    public void showPassword(){
//        save(null);
//    }


}
