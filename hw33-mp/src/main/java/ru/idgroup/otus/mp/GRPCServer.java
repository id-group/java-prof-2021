package ru.idgroup.otus.mp;


import io.grpc.ServerBuilder;
import ru.idgroup.otus.mp.service.RemoteSequenceServiceImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteSequenceService = new RemoteSequenceServiceImpl();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(remoteSequenceService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
