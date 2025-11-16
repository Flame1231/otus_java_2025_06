package ru.otus.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.GeneratingNumbersRequest;
import ru.otus.GeneratingNumbersResponse;
import ru.otus.GeneratingNumbersServiceGrpc;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class GeneratingNumbersServiceImpl extends GeneratingNumbersServiceGrpc.GeneratingNumbersServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(GeneratingNumbersServiceImpl.class);

    @Override
    public void getNumbers(GeneratingNumbersRequest request,
                           StreamObserver<GeneratingNumbersResponse> responseObserver) {

        log.info("generate numbers from {} to {}", request.getFirstValue(), request.getLastValue());

        AtomicLong currentNumber = new AtomicLong(request.getFirstValue());
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            try {
                long value = currentNumber.getAndIncrement();

                responseObserver.onNext(
                        GeneratingNumbersResponse.newBuilder().setNumber(value).build()
                );

                if (currentNumber.get() > request.getLastValue()) {
                    log.info("generate ends");
                    executor.shutdown();
                    responseObserver.onCompleted();
                }

            } catch (Exception e) {
                log.error("Error during number generation", e);

                responseObserver.onError(
                        Status.INTERNAL
                                .withDescription("Internal error during number generation")
                                .augmentDescription(e.getMessage())
                                .asRuntimeException()
                );
                executor.shutdown();
            }

        }, 0, 2, TimeUnit.SECONDS);
    }

}
