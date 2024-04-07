package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ServerMessage;

public class CustomStreamObserver implements StreamObserver<ServerMessage> {

    private long value;

    @Override
    public void onNext(ServerMessage serverMessage) {
        System.out.println("new value: " + serverMessage.getValue());
        setValue(serverMessage.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("ERROR:: /n" + throwable);
    }

    @Override
    public void onCompleted() {
        System.out.println("completed!");
    }

    public synchronized long getValue() {
        long currValue = value;
        value = 0;
        return currValue;
    }

    public synchronized void setValue(long value) {
        this.value = value;
    }
}
