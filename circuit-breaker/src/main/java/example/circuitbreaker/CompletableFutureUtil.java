package example.circuitbreaker;

import example.circuitbreaker.exceptions.CircuitBreakerTimeoutException;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class CompletableFutureUtil {

    private static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

    /**
     * Returns a CompletableFuture that completes with the result of the given future,
     * or completes exceptionally with a {@code CircuitBreakerTimeoutException} if the
     * given future does not complete within the specified timeout duration.
     * <p>
     * If the timeout duration is zero or negative, the given future is returned,
     * effectively bypassing the timeout behavior.
     *
     * @param <T>     the type of the result of the CompletableFuture
     * @param future  the original CompletableFuture to be wrapped with a timeout mechanism. Must not be null.
     * @param timeout the maximum amount of time to wait for the CompletableFuture to complete. Must not be null.
     *                If the value is zero or negative, no timeout will be applied.
     * @return a CompletableFuture that either completes with the result of the given future,
     * or completes exceptionally with a {@code CircuitBreakerTimeoutException} upon timeout.
     * @throws NullPointerException if {@code future} is null.
     */
    public static <T> CompletableFuture<T> timeOutAfter(CompletableFuture<T> future, Duration timeout) {
        Objects.requireNonNull(future);
        Objects.requireNonNull(timeout);

        // #1: infinite timeout or future already completed then treat as "no timeout"
        if (timeout.isZero() || timeout.isNegative()) {
            return future;
        }

        // #2: set up timeout future, throw exception if the future does not complete in timeout duration
        CompletableFuture<T> timeoutFuture = new CompletableFuture<>();
        ScheduledFuture<Boolean> timeoutHandle = scheduledExecutor.schedule(
                () -> timeoutFuture.completeExceptionally(new CircuitBreakerTimeoutException("Invocation time out")),
                timeout.toMillis(),
                TimeUnit.MILLISECONDS
        );

        // cancel timeout future task if the original future completes first
        future.whenComplete((t, throwable) -> {
            timeoutHandle.cancel(false);
            System.out.println("Cancelled timeout task");
        });

        return future.applyToEither(timeoutFuture, t -> t); // when either completes, return its result
    }
}
