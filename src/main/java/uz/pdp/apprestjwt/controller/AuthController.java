package uz.pdp.apprestjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apprestjwt.payload.LoginDto;
import uz.pdp.apprestjwt.security.JwtProvider;
import uz.pdp.apprestjwt.service.MyAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    MyAuthService myAuthService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> loginToSystem(@RequestBody LoginDto loginDto) {
//        UserDetails userDetails = myAuthService.loadUserByUsername(loginDto.getUsername());
//        if (passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
//            String token = jwtProvider.generateToken(loginDto.getUsername());
//            return ResponseEntity.ok(token);
//        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Login yoki parol xato !");
        }
    }
}
