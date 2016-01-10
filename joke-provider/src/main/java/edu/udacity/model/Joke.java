package edu.udacity.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("id", id).append("content", content);

        return builder.toString();
    }
}
