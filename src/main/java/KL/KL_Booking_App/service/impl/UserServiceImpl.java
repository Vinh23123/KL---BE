package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.exeption.ResourceNotFoundException;
import KL.KL_Booking_App.payload.request.RequestNewPassword;
import KL.KL_Booking_App.repository.UserRepository;
import KL.KL_Booking_App.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setPhone(updatedUser.getPhone());
                    existingUser.setEmail(updatedUser.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    }

    @Override
    public void updatePassword(RequestNewPassword requestNewPassword) {
        User user =  userRepository.findByUsername(requestNewPassword.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + requestNewPassword.getUserName()));
        user.setPassword(encoder.encode(requestNewPassword.getPassword()));
        userRepository.save(user);
    }
}
