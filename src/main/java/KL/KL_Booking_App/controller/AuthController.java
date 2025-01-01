package KL.KL_Booking_App.controller;

import KL.KL_Booking_App.config.JwtUtils;
import KL.KL_Booking_App.constants.Global;
import KL.KL_Booking_App.entity.RefreshToken;
import KL.KL_Booking_App.entity.Role;
import KL.KL_Booking_App.entity.User;
import KL.KL_Booking_App.entity.roleType.ERole;
import KL.KL_Booking_App.exeption.TokenRefreshException;
import KL.KL_Booking_App.payload.request.LoginRequest;
import KL.KL_Booking_App.payload.request.SignupRequest;
import KL.KL_Booking_App.payload.request.TokenRefreshRequest;
import KL.KL_Booking_App.payload.response.JwtResponse;
import KL.KL_Booking_App.payload.response.MessageResponse;
import KL.KL_Booking_App.payload.response.Response;
import KL.KL_Booking_App.payload.response.TokenRefreshResponse;
import KL.KL_Booking_App.repository.RoleRepository;
import KL.KL_Booking_App.repository.UserRepository;

import KL.KL_Booking_App.service.impl.RefreshTokenService;
import KL.KL_Booking_App.service.sec.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Response> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Response.builder()
                .status(Global.STATUS_SUCCESS)
                .data(new JwtResponse(jwt,
                        refreshToken.getToken(),
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles))
                .message(Global.MESSAGE_SIGN_IN_SUCCESSFULLY)
                .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(String.valueOf(new MessageResponse("Error: Username is already taken!")))
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build());
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Response.builder()
                            .status(Global.STATUS_FAILED)
                            .message(String.valueOf(new MessageResponse("Error: Email is already in use!")))
                            .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build());
        }

        // Create new user's account
        User user = new User(signUpRequest.getUserName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(Response.builder()
                        .status(Global.STATUS_SUCCESS)
                        .message(Global.MESSAGE_REGISTER_SUCCESSFULLY)
                        .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .build());
    }



    @PostMapping("/refreshtoken")
    public ResponseEntity<Response> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity
                            .ok(Response.builder()
                                    .status(Global.MESSAGE_GET_SUCCESSFULLY)
                                    .data(new TokenRefreshResponse(token, requestRefreshToken))
                                    .message(Global.MESSAGE_REFRESH_TOKEN_SUCCESSFULLY)
                                    .time(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                            .build());
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
