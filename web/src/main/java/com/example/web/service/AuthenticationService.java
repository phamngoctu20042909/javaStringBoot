package com.example.web.service;

import java.text.ParseException;
import java.util.Date;
import java.util.function.IntPredicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.web.dto.request.AuthenticationRequest;
import com.example.web.dto.request.IntrospectRequest;
import com.example.web.dto.response.AuthenticationResponse;
import com.example.web.dto.response.IntrospectResponse;
import com.example.web.entity.Role;
import com.example.web.entity.User;
import com.example.web.exception.AppException;
import com.example.web.exception.ErrorCode;
import com.example.web.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.var;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    public IntrospectResponse isIntrospect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var verify = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verify)
                .build();
    }

    public AuthenticationResponse isAuthenticated(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isauthen = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (isauthen) {
            var token = generateToken(user);

            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true).build();
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        }
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("javaweb.com")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 300 * 1000))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (KeyLengthException e) {
            throw new AppException(ErrorCode.TOKEN_GENERATION_ERROR);
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_GENERATION_ERROR);
        }
    }

    private String buildScope(User user) {
        StringBuilder scope = new StringBuilder();
        // for (Role role : user.getRoles()) {
        // if (scope.length() > 0) {
        // scope.append(" ");
        // }
        // scope.append(role);
        // }
        return scope.toString();
    }
}
