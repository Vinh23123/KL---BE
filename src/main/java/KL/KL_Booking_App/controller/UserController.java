package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.payload.request.RequestNewPassword;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.UserDto;
import KL.KL_Booking_App.service.IUserService;
import KL.KL_Booking_App.service.impl.MailJetServiceImpl;
import com.mailjet.client.errors.MailjetException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private MailJetServiceImpl mailJetService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") Long userId) {
        try {
            User user = userService.getUserById(userId);
            log.info("Successfully fetched user with ID: {}", userId);

            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(UserDto.builder()
                                    .userId(user.getUserId())
                                    .firstName(user.getFirstName())
                                    .lastName(user.getLastName())
                                    .phone(user.getPhone())
                                    .mail(user.getMail())
                                    .build())
                            .message(Global.MESSAGE_GET_SUCCESSFULLY)
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            log.error("Error fetching user with ID: {}", userId, e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message("An unexpected error occurred: " + e.getMessage())
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}/changePass")
    public ResponseEntity<Response> getUserByIdChangePass(@PathVariable("id") Long userId) {
        try {
            User user = userService.getUserById(userId);
            log.info("Successfully fetched user with ID: {}", userId);
            // testing sending mail
            mailJetService.sendMailTo();
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(UserDto.builder()
                                    .userId(user.getUserId())
                                    .firstName(user.getUsername())
                                    .build())
                            .message(Global.MESSAGE_GET_SUCCESSFULLY)
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            log.error("Error fetching user with ID: {}", userId, e);
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message("An unexpected error occurred: " + e.getMessage())
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (MailjetException e) {
            log.info("Mailjet error:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(
            @PathVariable("id") Long userId,
            @Validated @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            log.info("Successfully updated user with ID: {}", userId);

            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .data(UserDto.builder()
                                    .userId(updatedUser.getUserId())
                                    .firstName(updatedUser.getFirstName())
                                    .lastName(updatedUser.getLastName())
                                    .phone(updatedUser.getPhone())
                                    .mail(updatedUser.getMail())
                                    .build())
                            .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            log.error("Error updating user with ID: {}", userId, e);

            if (e.getMessage().contains("users.UK6dotkott2kjsp8vw4d0m25fb7")) {
                log.warn("Duplicate email constraint violated for user ID: {}", userId);
                return new ResponseEntity<>(
                        Response.builder()
                                .status(Global.STATUS_FAILED)
                                .message("Email already exists. Please use a different email address.")
                                .time(LocalDateTime.now().format(DATE_FORMATTER))
                                .build(),
                        HttpStatus.CONFLICT
                );
            }

            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message("An unexpected error occurred: " + e.getMessage())
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Response> updateNewPass(
            @Valid @RequestBody RequestNewPassword requestNewPassword) {
        try {
            userService.updatePassword(requestNewPassword);
            log.info("Successfully updated user with: {}", requestNewPassword.getUserName());

            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_SUCCESS)
                            .message(Global.MESSAGE_UPDATED_SUCCESSFULLY)
                            .time(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(e.getMessage())
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
