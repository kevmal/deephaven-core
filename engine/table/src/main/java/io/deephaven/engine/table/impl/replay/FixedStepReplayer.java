/**
 * Copyright (c) 2016-2022 Deephaven Data Labs and Patent Pending
 */
package io.deephaven.engine.table.impl.replay;

import io.deephaven.base.clock.Clock;
import io.deephaven.time.DateTime;
import io.deephaven.time.DateTimeUtils;

import java.time.Instant;

public class FixedStepReplayer extends Replayer {
    private long incrementNanos;
    private DateTime currentTime;

    public FixedStepReplayer(DateTime startTime, DateTime endTime, long incrementNanos) {
        super(startTime, endTime);
        this.incrementNanos = incrementNanos;
        currentTime = startTime;
    }

    @Override
    public void run() {
        currentTime = DateTimeUtils.plus(currentTime, incrementNanos);
        if (currentTime.getNanos() > endTime.getNanos()) {
            currentTime = endTime;
        }
        super.run();
    }

    @Override
    public void setTime(long updatedTime) {
        currentTime = DateTimeUtils.millisToTime(Math.max(updatedTime, currentTime.getMillis()));
    }

    @Override
    public Clock clock() {
        return new ClockImpl();
    }

    private class ClockImpl extends DateTimeClock {
        @Override
        public DateTime currentDateTime() {
            return currentTime;
        }
    }
}
