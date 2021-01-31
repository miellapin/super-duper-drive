package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(Credentials credentials) {
        credentialMapper.addCredential(credentials);
    }

    public List<Credentials> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }
}
