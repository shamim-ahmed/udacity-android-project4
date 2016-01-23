package com.udacity.gradle.builditbigger;

public interface AsyncTaskListener<T> {
    void onSuccess(T data);
}
