package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.payload.LoginRequestDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.security.Jwt.JwtResponse;
import com.synrgy.binarfud.Binarfud.security.Jwt.JwtUtils;
import com.synrgy.binarfud.Binarfud.security.service.UserDetailsImpl;
import com.synrgy.binarfud.Binarfud.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MailService mailService;

    @PostMapping("login")
    public ResponseEntity<Response> authenticate(@RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), roles);
        return new ResponseEntity<>(new Response.Success(jwtResponse), HttpStatus.CREATED);
    }

    @GetMapping("oauth2/success")
    public ResponseEntity<Response> googleLoginSuccess(Authentication authentication) {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        OidcUser modifiedOidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

        Authentication modifiedAuthentication = new UsernamePasswordAuthenticationToken(
                modifiedOidcUser,
                oidcUser.getIdToken(),
                authorities
        );

        String jwt = jwtUtils.generateToken(modifiedAuthentication);
        List<String> roles = modifiedAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtResponse jwtResponse = new JwtResponse(jwt, oidcUser.getPreferredUsername(), roles);
        return new ResponseEntity<>(new Response.Success(jwtResponse), HttpStatus.CREATED);
    }


    @GetMapping("/test/mail")
    public String sendMail() {
        mailService.sendMail("lckmnzans@gmail.com", "subject test", "Hello World");
        return "Mail sent";
    }
}
