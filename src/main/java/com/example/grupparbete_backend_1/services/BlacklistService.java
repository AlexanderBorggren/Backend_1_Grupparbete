package com.example.grupparbete_backend_1.services;

import java.io.IOException;

public interface BlacklistService {

    public boolean isEmailBlacklisted(String email) throws IOException, InterruptedException;




}
