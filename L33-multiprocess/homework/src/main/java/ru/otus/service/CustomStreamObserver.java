package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ServerMessage;

public class CustomStreamObserver implements StreamObserver<ServerMessage> {
    long value;

    @Override
    public void onNext(ServerMessage serverMessage) {
        this.value = serverMessage.getValue();
        System.out.println("new value: " + value);

    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("ERROR:: /n" + throwable);
    }

    @Override
    public void onCompleted() {
        System.out.println("completed!");
    }

    public long getValue() {
        long currValue = value;
        value = 0;
        return currValue;
    }
}
