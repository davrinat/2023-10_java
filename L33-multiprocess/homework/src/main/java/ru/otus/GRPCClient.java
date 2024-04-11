package ru.otus;

import io.grpc.ManagedChannelBuilder;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.service.CustomStreamObserver;

import java.util.concurrent.CountDownLatch;

@SuppressWarnings({"squid:S106", "squid:S1149"})
public class GRPCClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var latch = new CountDownLatch(1);
        var newStub = RemoteDBServiceGrpc.newStub(channel);

        var clientMessage =ClientMessage.newBuilder().setFirstValue(0).setLastValue(30).build();
        var observer = new CustomStreamObserver();

        newStub.getValue(clientMessage, observer);

        long currentValue = 0;

        for (long i = 0; i <= 50; i++) {
            if (i == 0) {
                Thread.sleep(1000);
                System.out.println("numbers of Client is starting...");
            }
            Thread.sleep(1000);
            currentValue = currentValue + observer.getValue() + 1;
            System.out.println("current value: " + currentValue);
        }
        latch.await();
        channel.shutdown();
    }
}
