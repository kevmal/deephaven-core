package io.deephaven.db.v2.tuples.generated;

import io.deephaven.datastructures.util.SmartKey;
import io.deephaven.db.util.tuples.generated.ObjectIntLongTuple;
import io.deephaven.db.v2.sources.ColumnSource;
import io.deephaven.db.v2.sources.WritableSource;
import io.deephaven.db.v2.sources.chunk.Attributes;
import io.deephaven.db.v2.sources.chunk.Chunk;
import io.deephaven.db.v2.sources.chunk.IntChunk;
import io.deephaven.db.v2.sources.chunk.LongChunk;
import io.deephaven.db.v2.sources.chunk.ObjectChunk;
import io.deephaven.db.v2.sources.chunk.WritableChunk;
import io.deephaven.db.v2.sources.chunk.WritableObjectChunk;
import io.deephaven.db.v2.tuples.AbstractTupleSource;
import io.deephaven.db.v2.tuples.ThreeColumnTupleSourceFactory;
import io.deephaven.db.v2.tuples.TupleSource;
import io.deephaven.util.type.TypeUtils;
import org.jetbrains.annotations.NotNull;


/**
 * <p>{@link TupleSource} that produces key column values from {@link ColumnSource} types Object, Integer, and Long.
 * <p>Generated by {@link io.deephaven.db.v2.tuples.TupleSourceCodeGenerator}.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ObjectIntegerLongColumnTupleSource extends AbstractTupleSource<ObjectIntLongTuple> {

    /** {@link ThreeColumnTupleSourceFactory} instance to create instances of {@link ObjectIntegerLongColumnTupleSource}. **/
    public static final ThreeColumnTupleSourceFactory<ObjectIntLongTuple, Object, Integer, Long> FACTORY = new Factory();

    private final ColumnSource<Object> columnSource1;
    private final ColumnSource<Integer> columnSource2;
    private final ColumnSource<Long> columnSource3;

    public ObjectIntegerLongColumnTupleSource(
            @NotNull final ColumnSource<Object> columnSource1,
            @NotNull final ColumnSource<Integer> columnSource2,
            @NotNull final ColumnSource<Long> columnSource3
    ) {
        super(columnSource1, columnSource2, columnSource3);
        this.columnSource1 = columnSource1;
        this.columnSource2 = columnSource2;
        this.columnSource3 = columnSource3;
    }

    @Override
    public final ObjectIntLongTuple createTuple(final long indexKey) {
        return new ObjectIntLongTuple(
                columnSource1.get(indexKey),
                columnSource2.getInt(indexKey),
                columnSource3.getLong(indexKey)
        );
    }

    @Override
    public final ObjectIntLongTuple createPreviousTuple(final long indexKey) {
        return new ObjectIntLongTuple(
                columnSource1.getPrev(indexKey),
                columnSource2.getPrevInt(indexKey),
                columnSource3.getPrevLong(indexKey)
        );
    }

    @Override
    public final ObjectIntLongTuple createTupleFromValues(@NotNull final Object... values) {
        return new ObjectIntLongTuple(
                values[0],
                TypeUtils.unbox((Integer)values[1]),
                TypeUtils.unbox((Long)values[2])
        );
    }

    @Override
    public final ObjectIntLongTuple createTupleFromReinterpretedValues(@NotNull final Object... values) {
        return new ObjectIntLongTuple(
                values[0],
                TypeUtils.unbox((Integer)values[1]),
                TypeUtils.unbox((Long)values[2])
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <ELEMENT_TYPE> void exportElement(@NotNull final ObjectIntLongTuple tuple, final int elementIndex, @NotNull final WritableSource<ELEMENT_TYPE> writableSource, final long destinationIndexKey) {
        if (elementIndex == 0) {
            writableSource.set(destinationIndexKey, (ELEMENT_TYPE) tuple.getFirstElement());
            return;
        }
        if (elementIndex == 1) {
            writableSource.set(destinationIndexKey, tuple.getSecondElement());
            return;
        }
        if (elementIndex == 2) {
            writableSource.set(destinationIndexKey, tuple.getThirdElement());
            return;
        }
        throw new IndexOutOfBoundsException("Invalid element index " + elementIndex + " for export");
    }

    @Override
    public final Object exportToExternalKey(@NotNull final ObjectIntLongTuple tuple) {
        return new SmartKey(
                tuple.getFirstElement(),
                TypeUtils.box(tuple.getSecondElement()),
                TypeUtils.box(tuple.getThirdElement())
        );
    }

    @Override
    public final Object exportElement(@NotNull final ObjectIntLongTuple tuple, int elementIndex) {
        if (elementIndex == 0) {
            return tuple.getFirstElement();
        }
        if (elementIndex == 1) {
            return TypeUtils.box(tuple.getSecondElement());
        }
        if (elementIndex == 2) {
            return TypeUtils.box(tuple.getThirdElement());
        }
        throw new IllegalArgumentException("Bad elementIndex for 3 element tuple: " + elementIndex);
    }

    @Override
    public final Object exportElementReinterpreted(@NotNull final ObjectIntLongTuple tuple, int elementIndex) {
        if (elementIndex == 0) {
            return tuple.getFirstElement();
        }
        if (elementIndex == 1) {
            return TypeUtils.box(tuple.getSecondElement());
        }
        if (elementIndex == 2) {
            return TypeUtils.box(tuple.getThirdElement());
        }
        throw new IllegalArgumentException("Bad elementIndex for 3 element tuple: " + elementIndex);
    }

    @Override
    protected void convertChunks(@NotNull WritableChunk<? super Attributes.Values> destination, int chunkSize, Chunk<Attributes.Values> [] chunks) {
        WritableObjectChunk<ObjectIntLongTuple, ? super Attributes.Values> destinationObjectChunk = destination.asWritableObjectChunk();
        ObjectChunk<Object, Attributes.Values> chunk1 = chunks[0].asObjectChunk();
        IntChunk<Attributes.Values> chunk2 = chunks[1].asIntChunk();
        LongChunk<Attributes.Values> chunk3 = chunks[2].asLongChunk();
        for (int ii = 0; ii < chunkSize; ++ii) {
            destinationObjectChunk.set(ii, new ObjectIntLongTuple(chunk1.get(ii), chunk2.get(ii), chunk3.get(ii)));
        }
        destinationObjectChunk.setSize(chunkSize);
    }

    /** {@link ThreeColumnTupleSourceFactory} for instances of {@link ObjectIntegerLongColumnTupleSource}. **/
    private static final class Factory implements ThreeColumnTupleSourceFactory<ObjectIntLongTuple, Object, Integer, Long> {

        private Factory() {
        }

        @Override
        public TupleSource<ObjectIntLongTuple> create(
                @NotNull final ColumnSource<Object> columnSource1,
                @NotNull final ColumnSource<Integer> columnSource2,
                @NotNull final ColumnSource<Long> columnSource3
        ) {
            return new ObjectIntegerLongColumnTupleSource(
                    columnSource1,
                    columnSource2,
                    columnSource3
            );
        }
    }
}