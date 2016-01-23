package edu.udacity.model;

import edu.udacity.util.StringUtils;

public class Joke implements Comparable<Joke> {
    private final Long id;
    private final String content;

    public Joke(Long id, String content) {
        if (id == null) {
            throw new IllegalArgumentException("Joke id cannot be null");
        }

        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Joke content cannot be blank");
        }

        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int compareTo(Joke joke) {
        if (id < joke.id) {
            return -1;
        }

        if (id > joke.id) {
            return 1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Joke)) {
            return false;
        }

        Joke otherJoke = (Joke) obj;
        return id.equals(otherJoke.id) && content.equals(otherJoke.content);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public String toString() {
        return String.format("%s@%s{\nid: %d;\ncontent: %s\n}",
                getClass().getCanonicalName(),
                Integer.toHexString(hashCode()), id, content);

    }
}
