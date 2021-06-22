package com.hauxi.project.springredditclone.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import java.util.Optional;
import java.time.Instant;
import java.util.UUID;                                                                      

import com.hauxi.project.springredditclone.dto.*;
import com.hauxi.project.springredditclone.exception.RedditException;
import com.hauxi.project.springredditclone.model.*;
import com.hauxi.project.springredditclone.repository.*;
import com.hauxi.project.springredditclone.security.JwtProvider;

@Service
@AllArgsConstructor
public class AuthService {

    //field injection
    // @Autowired
    // private PasswordEncoder passwordEncoder;
    // @Autowired
    // private UserRepository userRepository;
    //   alternative is to use constructor injection

    //constructor injection
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;    
    private final MailService mailService;
    private final AuthenticationManager authenticationManager; 
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(RegisterRequest registerRequest)
    {
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false); 
        userRepository.save(user);

        String token= generateVerificationToken (user);
        NotificationEmail notificationEmail = new NotificationEmail(
                                "Please Activate your Account",
                                 user.getEmail(),
                                "Thank you for signing up to Spring Reddit,please click on the below url to activate your account :"
                                + "http://localhost:8080/api/auth/accountVerification/"
                                +token
                                 ); 
        mailService.sendMail(notificationEmail);
    }

    public String generateVerificationToken(Users user)
    {
            String token=UUID.randomUUID().toString();
            VerificationToken verificationToken=new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);

            verificationTokenRepository.save(verificationToken);
            return token;
        }
        

        public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new RedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

     @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new RedditException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token=jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
        .expiresAt(Instant.now().minusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(loginRequest.getUsername())
        .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder().authenticationToken(token).refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(refreshTokenRequest.getUsername())
        .build();
    }

    @Transactional(readOnly = true)
    public Users getCurrentUser(){
        User principal=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("USer name not found: "+ principal.getUsername()));
    }

        public boolean isLoggedIn(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
