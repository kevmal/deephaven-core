/**
 * Copyright (c) 2016-2023 Deephaven Data Labs and Patent Pending
 */
package io.deephaven.engine.table.impl.sources;

import io.deephaven.chunk.attributes.Values;
import io.deephaven.engine.table.impl.DefaultChunkSource;
import io.deephaven.engine.table.impl.MutableColumnSourceGetDefaults;
import io.deephaven.time.DateTimeUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

/**
 * Sparse Array-backed ColumnSource for {@link Instant}s. Allows reinterpret as long.
 */
public class InstantSparseArraySource extends NanosBasedTimeSparseArraySource<Instant>
        implements MutableColumnSourceGetDefaults.ForLongAsInstant, DefaultChunkSource<Values>, ConvertableTimeSource {
    public InstantSparseArraySource() {
        super(Instant.class);
    }

    public InstantSparseArraySource(final @NotNull LongSparseArraySource nanoSource) {
        super(Instant.class, nanoSource);
    }

    @Override
    protected Instant makeValue(long nanos) {
        return DateTimeUtils.makeInstant(nanos);
    }

    @Override
    protected long toNanos(Instant value) {
        return DateTimeUtils.toEpochNano(value);
    }
}
