package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public void addCredential(Credentials credentials) {
        byte[] salt = new byte[16];
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credentials.getPassword(), encodedSalt);
        credentials.setKey(hashedPassword);
        credentialMapper.addCredential(credentials);
    }

    public List<Credentials> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }

    public void updateCredential(Credentials credentials) {
        credentialMapper.update(credentials);
    }
}
