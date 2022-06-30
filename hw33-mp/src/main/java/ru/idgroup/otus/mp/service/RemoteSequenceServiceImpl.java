package ru.idgroup.otus.mp.service;

import io.grpc.stub.StreamObserver;
import ru.idgroup.otus.mp.generated.RemoteSequenceGrpc;
import ru.idgroup.otus.mp.generated.RemoteSequenceOuterClass;

public class RemoteSequenceServiceImpl extends RemoteSequenceGrpc.RemoteSequenceImplBase {
    @Override
    public void getSequence(RemoteSequenceOuterClass.RequestSequenceMessage request, StreamObserver<RemoteSequenceOuterClass.GenNumberMessage> responseObserver) {
        for( long i = request.getFirstValue(); i<request.getLastValue(); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            responseObserver.onNext(
                RemoteSequenceOuterClass.GenNumberMessage.newBuilder().setValue(i).build()
            );
        }
        responseObserver.onCompleted();
    }
}
