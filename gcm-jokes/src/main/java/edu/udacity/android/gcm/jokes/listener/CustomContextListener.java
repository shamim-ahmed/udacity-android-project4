package edu.udacity.android.gcm.jokes.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.udacity.provider.jokes.JokeProvider;

public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initiate a joke retrieval so that all jokes are loaded
        // This will ensure that the first client does not experience significant delay
        JokeProvider.getJoke();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
