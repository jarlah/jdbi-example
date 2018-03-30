package com.example.demo;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@DefaultIntegrationTestAnnotations
public class RaceConditionTest {

    @Autowired
    private HttpClientUtils clientUtils;

    class Status {
        private boolean ok;
    }

    @Test
    @SneakyThrows
    public void testRaceConditionIsNotPresent() {
        CountDownLatch lock = new CountDownLatch(2);

        final CyclicBarrier gate = new CyclicBarrier(3);

        final Status status1 = new Status();
        final Thread t1 = new Thread() {
            @SneakyThrows
            public void run() {
                gate.await();
                ResponseEntity<String> res = clientUtils.get("/car",  String.class);
                assertTrue(res.getStatusCode().is2xxSuccessful());
                lock.countDown();
                status1.ok = true;
            }
        };

        final Status status2 = new Status();
        final Thread t2 = new Thread() {
            @SneakyThrows
            public void run() {
                gate.await();
                ResponseEntity<String> res = clientUtils.get("/car", String.class);
                assertTrue(res.getStatusCode().is2xxSuccessful());
                lock.countDown();
                status2.ok = true;
            }
        };

        t2.start();
        t1.start();
        gate.await();
        lock.await(10000, TimeUnit.MILLISECONDS);
        assertTrue(status1.ok);
        assertTrue(status2.ok);
    }
}
