package com.example.jainshaadi;

import android.content.Context;
import android.text.format.DateUtils;

public class Messages {

    private String message;
    private String senderId;
    private long timestamp;
    private String currenttime;

    // Default constructor required for Firebase deserialization
    public Messages() {
    }

    // Modified constructor with Context parameter
    public Messages(String message, String senderId, long timestamp, Context context) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.currenttime = formatTimestamp(timestamp, context);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp, Context context) {
        this.timestamp = timestamp;
        this.currenttime = formatTimestamp(timestamp, context);
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }

    private String formatTimestamp(long timestamp, Context context) {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Check if the message is from today
        if (DateUtils.isToday(timestamp)) {
            // Format time in hours and minutes
            return DateUtils.formatDateTime(
                    context, timestamp, DateUtils.FORMAT_SHOW_TIME);
        }

        // Check if the message is from yesterday
        if (DateUtils.isToday(timestamp + DateUtils.DAY_IN_MILLIS)) {
            return "yest.";
        }

        // If it's older, format the date in words
        return DateUtils.formatDateTime(
                context, timestamp, DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_YEAR);
    }
}
