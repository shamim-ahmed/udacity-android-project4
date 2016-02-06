package edu.udacity.gradle.builditbigger.tasks;

import android.test.InstrumentationTestCase;

import edu.udacity.gradle.builditbigger.AsyncTaskListener;
import edu.udacity.gradle.builditbigger.FetchJokeTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FetchJokeTaskTest extends InstrumentationTestCase {
    private static final String DEFAULT_SERVICE_URL = "https://gcm-jokes.appspot.com/_ah/api/jokesApi/v1/retrieveJoke";
    private static final int DELAY_IN_SECONDS = 15;
    private boolean called;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    public void testSuccessfulFetch() throws Throwable {
        final String serviceUrl = System.getProperty("service.endpoint.url", DEFAULT_SERVICE_URL);
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

                task.execute(serviceUrl);
            }
        });

        latch.await(DELAY_IN_SECONDS, TimeUnit.SECONDS);
        assertTrue(called);
    }
}
