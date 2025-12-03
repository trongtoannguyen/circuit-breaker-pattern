package example.circuitbreaker.states;

import example.circuitbreaker.CircuitBreakerInvoker;
import example.circuitbreaker.CircuitBreakerSwitch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosedCircuitBreakerStateTest {

    //@formatter:off
    private static final Duration TIMEOUT = Duration.ofMillis(100);
    private static final int MAX_FAILURES = 3;
    @Mock private CircuitBreakerSwitch switcher;
    @Mock private CircuitBreakerInvoker invoker;
    private ClosedCircuitBreakerState sut;
    //@formatter:on

    @BeforeEach
    void setUp() {
        sut = new ClosedCircuitBreakerState(switcher, invoker, MAX_FAILURES, TIMEOUT);
    }

    @Test
    void enter() {
        sut.invocationFails();
        assertEquals(1, sut.getFailures().get());

        sut.enter();
        assertEquals(0, sut.getFailures().get());
    }

    @Test
    void invocationFails() {
        for (int i = 0; i < MAX_FAILURES - 1; i++) {
            sut.invocationFails();
            assertEquals(i + 1, sut.getFailures().get());
        }
    }

    @Test
    void invocationSucceeds() {
        sut.invocationFails();
        assertEquals(1, sut.getFailures().get());

        sut.invocationSucceeds();
        assertEquals(0, sut.getFailures().get());
    }
}