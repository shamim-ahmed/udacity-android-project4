package edu.udacity.android.gcm.jokes.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.udacity.model.Joke;
import edu.udacity.provider.jokes.JokeProvider;

public class CustomContextListener implements ServletContextListener {
    private final Logger logger = Logger.getLogger(CustomContextListener.class.getCanonicalName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("The context is being initialized...");
        // Initiate a joke retrieval so that all jokes are loaded
        // This will ensure that the first client does not experience significant delay
        Joke joke = JokeProvider.getJoke();

        if (joke != null) {
            logger.info("The internally retrieved joke is " + joke);
        } else {
            logger.warning("No joke was retrieved");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("The context is being destroyed...");
    }
}
