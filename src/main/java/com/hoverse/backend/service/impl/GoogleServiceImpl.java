package com.hoverse.backend.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/09/2026
 */
@Service
@RequiredArgsConstructor
public class GoogleServiceImpl implements GoogleService {

    private final GoogleIdTokenVerifier verifier;

    @Override
    public GoogleIdToken.Payload verifyCredential(String credential) {
        try {
            GoogleIdToken idToken = verifier.verify(credential);

            if(idToken == null){
                throw new BadRequestException("Google credential không hợp lệ");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            if(!Boolean.TRUE.equals(payload.getEmailVerified())){
                throw new BadRequestException("Email Google chưa được xác thực");
            }

            return payload;
        } catch (GeneralSecurityException | IOException e) {
            throw new BadRequestException("Không thể xác thực Google: "+ e);
        }
    }
}
