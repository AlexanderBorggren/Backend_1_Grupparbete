package com.example.grupparbete_backend_1.services;

import java.io.IOException;
import java.net.URISyntaxException;

public interface BlacklistService {

    public boolean isBlacklistOk(String email) throws IOException, InterruptedException, URISyntaxException;
}
