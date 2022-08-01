package com.betvictor.exchangerate.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class TrackRestData {
    public TrackRestData(LocalDateTime timestamp, String url, String result) {
        this.timestamp = timestamp;
        this.url = url;
        this.result = result;
    }

    private LocalDateTime timestamp;
    private String url;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackRestData that = (TrackRestData) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
