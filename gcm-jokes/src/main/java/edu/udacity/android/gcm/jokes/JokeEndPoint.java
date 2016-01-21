/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package edu.udacity.android.gcm.jokes;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import edu.udacity.model.Joke;
import edu.udacity.provider.jokes.JokeProvider;

/** An endpoint class we are exposing */
@Api(
  name = "jokesApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "jokes.gcm.android.udacity.edu",
    ownerName = "jokes.gcm.android.udacity.edu",
    packagePath=""
  )
)
public class JokeEndPoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "retrieveJoke")
    public Joke retrieveJoke() {
        return JokeProvider.getJoke();
    }
}
