package com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC.utils;

import java.util.Date;

public class Logger {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Logger(Class logClass) {
        this.className = logClass.getName();
    }

    public void info(String message) {
        this.log(message, "info");
    }

    public void debug(String message) {
        this.log(message, "debug");
    }

    public void error(String message) {
        this.log(message, "error");
    }

    public void warn(String message) {
        this.log(message, "warn");
    }

    private void log(String message, String logLevel) {

        String logString = "[" + new Date() + " - " +
                this.className + " - " +
                logLevel.toUpperCase() + "]" +
                " - " + message;

        System.out.println(logString);
    }
}
