package com.myshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.constant.Constants;
import com.myshop.request.model.AuthenticationRequest;
import com.myshop.security.JwtUtil;
import com.myshop.security.UserSecurityService;

@RestController
public class AuthenticationRestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSecurityService userSecurityService;

    @PostMapping("/authenticate")
    public String creteToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception(Constants.BAD_CREDIENTIAL, ex);
        }
        final UserDetails userDetails = userSecurityService.loadUserByUsername(request.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }
}
