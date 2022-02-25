package io.deephaven.db.v2.tuples.generated;

import io.deephaven.datastructures.util.SmartKey;
import io.deephaven.db.util.tuples.generated.DoubleByteTuple;
import io.deephaven.db.v2.sources.ColumnSource;
import io.deephaven.db.v2.sources.WritableSource;
import io.deephaven.db.v2.sources.chunk.Attributes;
import io.deephaven.db.v2.sources.chunk.ByteChunk;
import io.deephaven.db.v2.sources.chunk.Chunk;
import io.deephaven.db.v2.sources.chunk.DoubleChunk;
import io.deephaven.db.v2.sources.chunk.ObjectChunk;
import io.deephaven.db.v2.sources.chunk.WritableChunk;
import io.deephaven.db.v2.sources.chunk.WritableObjectChunk;
import io.deephaven.db.v2.tuples.AbstractTupleSource;
import io.deephaven.db.v2.tuples.TupleSource;
import io.deephaven.db.v2.tuples.TwoColumnTupleSourceFactory;
import io.deephaven.util.type.TypeUtils;
import org.jetbrains.annotations.NotNull;


/**
 * <p>{@link TupleSource} that produces key column values from {@link ColumnSource} types Double and Byte.
 * <p>Generated by {@link io.deephaven.db.v2.tuples.TupleSourceCodeGenerator}.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DoubleByteColumnTupleSource extends AbstractTupleSource<DoubleByteTuple> {

    /** {@link TwoColumnTupleSourceFactory} instance to create instances of {@link DoubleByteColumnTupleSource}. **/
    public static final TwoColumnTupleSourceFactory<DoubleByteTuple, Double, Byte> FACTORY = new Factory();

    private final ColumnSource<Double> columnSource1;
    private final ColumnSource<Byte> columnSource2;

    public DoubleByteColumnTupleSource(
            @NotNull final ColumnSource<Double> columnSource1,
            @NotNull final ColumnSource<Byte> columnSource2
    ) {
        super(columnSource1, columnSource2);
        this.columnSource1 = columnSource1;
        this.columnSource2 = columnSource2;
    }

    @Override
    public final DoubleByteTuple createTuple(final long indexKey) {
        return new DoubleByteTuple(
                columnSource1.getDouble(indexKey),
                columnSource2.getByte(indexKey)
        );
    }

    @Override
    public final DoubleByteTuple createPreviousTuple(final long indexKey) {
        return new DoubleByteTuple(
                columnSource1.getPrevDouble(indexKey),
                columnSource2.getPrevByte(indexKey)
        );
    }

    @Override
    public final DoubleByteTuple createTupleFromValues(@NotNull final Object... values) {
        return new DoubleByteTuple(
                TypeUtils.unbox((Double)values[0]),
                TypeUtils.unbox((Byte)values[1])
        );
    }

    @Override
    public final DoubleByteTuple createTupleFromReinterpretedValues(@NotNull final Object... values) {
        return new DoubleByteTuple(
                TypeUtils.unbox((Double)values[0]),
                TypeUtils.unbox((Byte)values[1])
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <ELEMENT_TYPE> void exportElement(@NotNull final DoubleByteTuple tuple, final int elementIndex, @NotNull final WritableSource<ELEMENT_TYPE> writableSource, final long destinationIndexKey) {
        if (elementIndex == 0) {
            writableSource.set(destinationIndexKey, tuple.getFirstElement());
            return;
        }
        if (elementIndex == 1) {
            writableSource.set(destinationIndexKey, tuple.getSecondElement());
            return;
        }
        throw new IndexOutOfBoundsException("Invalid element index " + elementIndex + " for export");
    }

    @Override
    public final Object exportToExternalKey(@NotNull final DoubleByteTuple tuple) {
        return new SmartKey(
                TypeUtils.box(tuple.getFirstElement()),
                TypeUtils.box(tuple.getSecondElement())
        );
    }

    @Override
    public final Object exportElement(@NotNull final DoubleByteTuple tuple, int elementIndex) {
        if (elementIndex == 0) {
            return TypeUtils.box(tuple.getFirstElement());
        }
        if (elementIndex == 1) {
            return TypeUtils.box(tuple.getSecondElement());
        }
        throw new IllegalArgumentException("Bad elementIndex for 2 element tuple: " + elementIndex);
    }

    @Override
    public final Object exportElementReinterpreted(@NotNull final DoubleByteTuple tuple, int elementIndex) {
        if (elementIndex == 0) {
            return TypeUtils.box(tuple.getFirstElement());
        }
        if (elementIndex == 1) {
            return TypeUtils.box(tuple.getSecondElement());
        }
        throw new IllegalArgumentException("Bad elementIndex for 2 element tuple: " + elementIndex);
    }

    protected void convertChunks(@NotNull WritableChunk<? super Attributes.Values> destination, int chunkSize, Chunk<Attributes.Values> [] chunks) {
        WritableObjectChunk<DoubleByteTuple, ? super Attributes.Values> destinationObjectChunk = destination.asWritableObjectChunk();
        DoubleChunk<Attributes.Values> chunk1 = chunks[0].asDoubleChunk();
        ByteChunk<Attributes.Values> chunk2 = chunks[1].asByteChunk();
        for (int ii = 0; ii < chunkSize; ++ii) {
            destinationObjectChunk.set(ii, new DoubleByteTuple(chunk1.get(ii), chunk2.get(ii)));
        }
        destination.setSize(chunkSize);
    }

    /** {@link TwoColumnTupleSourceFactory} for instances of {@link DoubleByteColumnTupleSource}. **/
    private static final class Factory implements TwoColumnTupleSourceFactory<DoubleByteTuple, Double, Byte> {

        private Factory() {
        }

        @Override
        public TupleSource<DoubleByteTuple> create(
                @NotNull final ColumnSource<Double> columnSource1,
                @NotNull final ColumnSource<Byte> columnSource2
        ) {
            return new DoubleByteColumnTupleSource(
                    columnSource1,
                    columnSource2
            );
        }
    }
}