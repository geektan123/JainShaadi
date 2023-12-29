package com.jainmaitri.app;

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

    public  String convertTimestampToString(long timestamp) {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Calculate the time difference in milliseconds
        long timeDifference = currentTime - timestamp;

        // Calculate time units
        long seconds = timeDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = days / 365;

        // Choose the appropriate format based on the time difference
        if (years > 0) {
            return years + "y";
        } else if (months > 0) {
            return months + "mo";
        } else if (days > 0) {
            return days + "d";
        } else if (hours > 0) {
            return hours + "h";
        } else if (minutes > 0) {
            return minutes + "min";
        } else {
            return "now";
        }
    }
    public String getCurrenttime() {
        return convertTimestampToString(timestamp);
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
