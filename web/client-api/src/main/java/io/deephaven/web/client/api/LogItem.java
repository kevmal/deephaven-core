/**
 * Copyright (c) 2016-2022 Deephaven Data Labs and Patent Pending
 */
package io.deephaven.web.client.api;

import com.vertispan.tsdefs.annotations.TsInterface;
import com.vertispan.tsdefs.annotations.TsName;
import jsinterop.annotations.JsProperty;

import java.io.Serializable;

/**
 * Represents a serialized fishlib LogRecord, suitable for display on javascript clients.
 */
@TsInterface
@TsName(namespace = "dh.ide")
public class LogItem implements Serializable {

    private double micros; // not using long, as js numbers are all floating point anyway

    private String logLevel; // not an enum because fishlib LogLevel is a class that allows you to create your own

    private String message;

    @JsProperty
    public double getMicros() {
        return micros;
    }

    public void setMicros(double timestamp) {
        this.micros = timestamp;
    }

    @JsProperty
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @JsProperty
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
