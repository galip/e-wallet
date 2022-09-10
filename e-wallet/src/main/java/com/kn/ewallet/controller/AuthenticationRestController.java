package com.kn.ewallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kn.ewallet.security.AuthenticationResponse;
import com.kn.ewallet.security.JwtUtil;
import com.kn.ewallet.service.UserSecurityService;

@RestController
public class AuthenticationRestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSecurityService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> creteToken(@RequestBody com.kn.ewallet.security.AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("BAD_CREDIENTIAL", ex);
        }
        final UserDetails userDetails = userService.loadUserByUsername(request.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
