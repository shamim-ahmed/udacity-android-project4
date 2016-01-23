package com.udacity.gradle.builditbigger.tasks;

import android.test.InstrumentationTestCase;

import com.udacity.gradle.builditbigger.AsyncTaskListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FetchJokeTaskTest extends InstrumentationTestCase {
    private static final String TEST_SERVICE_URL = "https://gcm-jokes.appspot.com/_ah/api/jokesApi/v1/retrieveJoke";
    private boolean called;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    public void testSuccessfulFetch() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                FetchJokeTask task = new FetchJokeTask(new AsyncTaskListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        assertNotNull(data);
                        assertTrue(!data.trim().equals(""));
                        called = true;
                    }
                });

                task.execute(TEST_SERVICE_URL);
            }
        });

        latch.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }
}
