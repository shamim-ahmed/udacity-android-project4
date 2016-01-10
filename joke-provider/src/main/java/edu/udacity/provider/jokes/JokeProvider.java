package edu.udacity.provider.jokes;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import edu.udacity.model.Joke;
import edu.udacity.util.IOUtils;

public class JokeProvider {
    private static final Logger LOGGER = Logger.getLogger(JokeProvider.class.getSimpleName());
    private static final List<Joke> jokeList = Collections.synchronizedList(new ArrayList<Joke>());
    private static final String JOKE_XPATH_EXPR = "/jokes/joke";
    private static final String ID_XPATH_EXPR = "id/text()";
    private static final String CONTENT_XPATH_EXPR = "content/text()";

    private static final String JOKE_TAG_NAME = "joke";

    static {
        loadJokes();
    }

    private synchronized static void loadJokes() {
        // this statement was included to get rid of a warning message. For details, please see
        // stackoverflow.com/questions/13407006/android-class-loader-may-fail-for-processes-that-host-multiple-applications
        Thread.currentThread().setContextClassLoader(JokeProvider.class.getClassLoader());

        InputStream inputStream = null;

        try {
            inputStream = JokeProvider.class.getResourceAsStream("/edu/udacity/provider/jokes/jokes.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(inputStream);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath jokePath = xpathFactory.newXPath();
            NodeList nodeList = (NodeList) jokePath.evaluate(JOKE_XPATH_EXPR, document, XPathConstants.NODESET);

            if (nodeList == null) {
                return;
            }

            for (int i = 0, n = nodeList.getLength(); i < n; i++) {
                Node node = nodeList.item(i);

                if (node instanceof Element) {
                    Element element = (Element) node;

                    if (element.getTagName().equals(JOKE_TAG_NAME)) {
                        XPath idPath = xpathFactory.newXPath();
                        String idStr = idPath.evaluate(ID_XPATH_EXPR, element);
                        String text = idPath.evaluate(CONTENT_XPATH_EXPR, element);

                        if (StringUtils.isBlank(idStr) || !StringUtils.isNumeric(idStr)) {
                            LOGGER.log(Level.WARNING, String.format("invalid value %s found for id", idStr));
                        } else if (StringUtils.isBlank(text)) {
                            LOGGER.log(Level.WARNING, String.format("invalid value %s found for id", idStr));
                        } else {
                            Long id = Long.valueOf(idStr);
                            Joke joke = new Joke(id, text.trim());
                            jokeList.add(joke);
                            LOGGER.log(Level.INFO, String.format("Loaded joke with id %s", idStr));
                        }
                    }
                }
            }

            // sort the jokes
            Collections.sort(jokeList);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            LOGGER.log(Level.SEVERE, "Error while parsing the XML retrieved from internal resource", ex);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static Joke getJoke() {
        Joke joke = null;
        final int n = jokeList.size();

        if (n > 0) {
            ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();
            int idx = randomGenerator.nextInt(n);
            joke = jokeList.get(idx);
        }

        return joke;
    }

    // private constructor to prevent instantiation
    private JokeProvider() {
    }
}
