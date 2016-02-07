package edu.udacity.gradle.builditbigger.tasks;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import edu.udacity.gradle.builditbigger.AsyncTaskListener;
import edu.udacity.gradle.builditbigger.FetchJokeTask;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FetchJokeTaskTest extends InstrumentationTestCase {
    private static final int DELAY_IN_SECONDS = 15;
    private boolean validated;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        validated = false;
    }

    public void testSuccessfulFetch() throws Throwable {
        AssetManager assetManager = getInstrumentation().getContext().getAssets();
        InputStream in = assetManager.open("test.properties");
        Properties properties = new Properties();
        properties.load(in);

        String remoteUrl = properties.getProperty("remote.service.endpoint.url");
        String localUrl = properties.getProperty("local.service.endpoint.url");
        final String targetUrl;

        if (!isBlank(localUrl)) {
            targetUrl = localUrl;
        } else {
            targetUrl = remoteUrl;
        }

        final CountDownLatch latch = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                FetchJokeTask task = new FetchJokeTask(new AsyncTaskListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        assertTrue(!isBlank(data));
                        validated = true;
                    }
                });

                task.execute(targetUrl);
            }
        });

        latch.await(DELAY_IN_SECONDS, TimeUnit.SECONDS);
        assertTrue(validated);
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }
}
