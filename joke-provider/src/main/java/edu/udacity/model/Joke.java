package edu.udacity.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Joke implements Comparable<Joke> {
    private final Long id;
    private final String text;

    public Joke(Long id, String text) {
        if (id == null) {
            throw new IllegalArgumentException("Joke id cannot be null");
        }

        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("Joke text cannot be blank");
        }

        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
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
        return id.equals(otherJoke.id) && text.equals(otherJoke.text);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("id", id).append("text", text);

        return builder.toString();
    }
}
