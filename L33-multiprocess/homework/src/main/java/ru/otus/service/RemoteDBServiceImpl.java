package ru.otus.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.ServerMessage;

@SuppressWarnings({"squid:S2142"})
@Slf4j
public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase implements RemoteDBService {
    @Override
    public void getValue(ClientMessage request, StreamObserver<ServerMessage> responseObserver) {

        for (long i = request.getFirstValue(); i <= request.getLastValue(); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
            responseObserver.onNext(serverMessage(i));
        }

        responseObserver.onCompleted();
    }

    private ServerMessage serverMessage(long value) {
        return ServerMessage.newBuilder()
                .setValue(value)
                .build();
    }
}
