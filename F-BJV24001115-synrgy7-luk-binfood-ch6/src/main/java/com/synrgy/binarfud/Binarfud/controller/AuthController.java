package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.payload.LoginRequestDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.security.Jwt.JwtResponse;
import com.synrgy.binarfud.Binarfud.security.Jwt.JwtUtils;
import com.synrgy.binarfud.Binarfud.security.Role;
import com.synrgy.binarfud.Binarfud.security.service.UserDetailsImpl;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDto loginRequest) {
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
        return new ResponseEntity<>(new Response.Success(jwtResponse), HttpStatus.OK);
    }
}
