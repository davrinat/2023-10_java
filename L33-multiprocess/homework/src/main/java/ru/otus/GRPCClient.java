package ru.otus;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientMessage;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.ServerMessage;

import java.util.Stack;
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
        Stack<ServerMessage> serverMessageStack = new Stack<>();

        newStub.getValue(ClientMessage.newBuilder().setFirstValue(0).setLastValue(30).build(), new StreamObserver<ServerMessage>() {

            @Override
            public void onNext(ServerMessage value) {
                serverMessageStack.push(value);
                System.out.println("new value: " + value.getValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("ERROR:: /n" + t);
            }

            @Override
            public void onCompleted() {
                System.out.println("completed!");
            }
        });

        long currentValue = 0;

        for (long i = 0; i <= 50; i++) {
            if (i == 0) {
                System.out.println("numbers of Client is starting...");
                Thread.sleep(1000);
            }
            Thread.sleep(1000);
            long serverValue = serverMessageStack.empty() ? 0 : serverMessageStack.pop().getValue();
            currentValue = currentValue + serverValue + 1;
            System.out.println("current value: " + currentValue);
        }

        latch.await();

        channel.shutdown();
    }
}
