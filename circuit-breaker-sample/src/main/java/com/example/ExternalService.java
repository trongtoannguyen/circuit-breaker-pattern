package com.example;

import java.util.concurrent.CompletableFuture;

public class ExternalService {

    public void get() {
        throw new RuntimeException("External service failed");
    }

    public CompletableFuture<Void> getAsync() {
        return CompletableFuture.failedFuture(
                new RuntimeException("Async External service failed")
        );
    }
}
