package com.viktor.yurlov.exeption;

public class JobException extends RuntimeException {

    public JobException() {
        super();
    }

    public JobException(String s) {
        super(s);
    }

    public JobException(Throwable throwable) {
        super(throwable);
    }

}
