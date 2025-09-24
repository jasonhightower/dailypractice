package com.hightower.dailypractice.core;

public class NotImplementedException extends RuntimeException {

    static String getCallerName() {
        int pos = 0;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        while (pos < stackTraceElements.length) {
            if (stackTraceElements[pos++].getClassName().equals(NotImplementedException.class.getName())) {
                break;
            }
        }
        while (pos < stackTraceElements.length) {
            String className = stackTraceElements[pos].getClassName();
            String methodName = stackTraceElements[pos++].getMethodName();
            if (!className.equals(NotImplementedException.class.getName())) {
                int lastDot = className.lastIndexOf('.');
                return (lastDot == -1 ? className : className.substring(lastDot + 1)) + "." + methodName;
            }
        }
        return "Unknown";
    }

    public NotImplementedException() {
        this(getCallerName() + " has not been implemented");
    }

    public NotImplementedException(final String message) {
        super(message);
    }

}
