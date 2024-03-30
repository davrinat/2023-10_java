package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.ServerMessage;

public interface RemoteDBService {
    void getValue(ClientMessage request, StreamObserver<ServerMessage> responseObserver);
}
