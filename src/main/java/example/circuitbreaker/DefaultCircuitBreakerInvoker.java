package example.circuitbreaker;

import example.circuitbreaker.states.CircuitBreakerState;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class DefaultCircuitBreakerInvoker implements CircuitBreakerInvoker {

    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> timerHandle;

    public DefaultCircuitBreakerInvoker() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public DefaultCircuitBreakerInvoker(ScheduledExecutorService scheduler) {
        this.scheduler = Objects.requireNonNull(scheduler);
    }


    @Override
    public void invokeScheduled(Runnable action, Duration interval) {
        Objects.requireNonNull(action);
        cancelTimerIfNeeded(); // Cancel any existing timer
        timerHandle = scheduler.schedule(action, interval.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void invokeThrough(CircuitBreakerState state, Runnable action, Duration timeout) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(action);
        try {
            invoke(action, timeout);
        } catch (Exception e) {
            state.invocationFails();
            throw e;
        }

        state.invocationSucceeds();
    }

    private void invoke(Runnable action, Duration timeout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeThrough(CircuitBreakerState state, Supplier<T> func, Duration timeout) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(func);
        T result;
        try {
            result = invoke(func, timeout);
        } catch (Exception e) {
            state.invocationFails();
            throw e;
        }

        state.invocationSucceeds();
        return result;
    }

    private <T> T invoke(Supplier<T> func, Duration timeout) {
        throw new UnsupportedOperationException();
    }


    @Override
    public <T> CompletableFuture<T> invokeThroughAsync(CircuitBreakerState state, Supplier<CompletableFuture<T>> func, Duration timeout) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(func);
        CompletableFuture<T> future;
        try {
            future = invokeAsync(func, timeout);
        } catch (Exception e) {
            state.invocationFails();
            throw e;
        }

        return future.whenComplete((t, throwable) -> {
            if (Objects.isNull(throwable)) {
                state.invocationSucceeds();
            } else {
                state.invocationFails();
            }
        });
    }

    private <T> CompletableFuture<T> invokeAsync(Supplier<CompletableFuture<T>> func, Duration timeout) {
        throw new UnsupportedOperationException();
    }

    //helper method to cancel any existing timer
    private void cancelTimerIfNeeded() {
        if (timerHandle != null) {
            timerHandle.cancel(true);
        }
    }
}
