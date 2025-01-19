package KL.KL_Booking_App.service;

import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.payload.request.RequestNewPassword;
import KL.KL_Booking_App.payload.response.UserDto;
import aj.org.objectweb.asm.commons.Remapper;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User updateUser(Long userId, User updatedUser);

    User getUserById(Long userId);

    void updatePassword(RequestNewPassword requestNewPassword);
}
