package com.hoverse.backend.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/09/2026
 */
public interface GoogleService {
    GoogleIdToken.Payload verifyCredential(String credential);
}
