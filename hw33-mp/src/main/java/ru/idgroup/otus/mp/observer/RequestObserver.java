package ru.idgroup.otus.mp.observer;

import io.grpc.stub.StreamObserver;
import ru.idgroup.otus.mp.GRPCClient;
import ru.idgroup.otus.mp.generated.RemoteSequenceOuterClass;

public class RequestObserver implements StreamObserver<RemoteSequenceOuterClass.GenNumberMessage> {
    private GRPCClient grpcClient;

    public RequestObserver(GRPCClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @Override
    public void onNext(RemoteSequenceOuterClass.GenNumberMessage value) {
        System.out.printf( "{получил: %d} %n", value.getValue());
        grpcClient.incCurrentNumber(value.getValue());
    }

    @Override
    public void onError(Throwable t) {
        System.err.println(t);
    }

    @Override
    public void onCompleted() {
        System.out.println("\n\nЯ все!");

    }
}
