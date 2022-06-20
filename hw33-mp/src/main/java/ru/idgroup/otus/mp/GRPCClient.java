package ru.idgroup.otus.mp;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.idgroup.otus.mp.generated.RemoteSequenceGrpc;
import ru.idgroup.otus.mp.generated.RemoteSequenceOuterClass;
import ru.idgroup.otus.mp.observer.RequestObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    public static final long START_VALUE = 0l;
    public static final long END_VALUE = 30l;

    private long currentValue = 0;
    private long lastServerValue = 0;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var requestSequenceMessage = RemoteSequenceOuterClass.RequestSequenceMessage.newBuilder()
                .setFirstValue(START_VALUE)
                .setLastValue(END_VALUE)
                .build();

        var grpcClient = new GRPCClient();
        grpcClient.go();

        var stub = RemoteSequenceGrpc.newStub(channel);
        stub.getSequence(requestSequenceMessage, new RequestObserver(grpcClient));
    }

    private void go() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for(int i=0;i<50;i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printValue(i);
            }
        });

        thread1.start();
    }

    public void incCurrentNumber( long incValue ) {
        lastServerValue = incValue;
    }

    public synchronized void printValue( long currentValue ) {
        System.out.printf("{currentValue: %d}\n", currentValue + lastServerValue + 1);
    }
}
