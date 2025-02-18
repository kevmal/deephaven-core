/**
 * Copyright (c) 2016-2022 Deephaven Data Labs and Patent Pending
 */
/*
 * ---------------------------------------------------------------------------------------------------------------------
 * AUTO-GENERATED CLASS - DO NOT EDIT MANUALLY - for any changes edit ParquetColumnRegionChar and regenerate
 * ---------------------------------------------------------------------------------------------------------------------
 */
package io.deephaven.parquet.table.region;

import io.deephaven.engine.table.impl.locations.TableDataException;
import io.deephaven.engine.table.impl.sources.regioned.ColumnRegionShort;
import io.deephaven.parquet.table.pagestore.ColumnChunkPageStore;
import io.deephaven.chunk.attributes.Any;
import io.deephaven.engine.page.ChunkPage;
import org.jetbrains.annotations.NotNull;

/**
 * {@link ColumnRegionShort} implementation for regions that support fetching primitive shorts from
 * {@link ColumnChunkPageStore column chunk page stores}.
 */
public final class ParquetColumnRegionShort<ATTR extends Any> extends ParquetColumnRegionBase<ATTR>
        implements ColumnRegionShort<ATTR>, ParquetColumnRegion<ATTR> {

    public ParquetColumnRegionShort(@NotNull final ColumnChunkPageStore<ATTR> columnChunkPageStore) {
        super(columnChunkPageStore.mask(), columnChunkPageStore);
    }

    @Override
    public short getShort(final long elementIndex) {
        final ChunkPage<ATTR> page = getChunkPageContaining(elementIndex);
        try {
            return page.asShortChunk().get(page.getChunkOffset(elementIndex));
        } catch (Exception e) {
            throw new TableDataException("Error retrieving short at table short rowSet " + elementIndex
                    + ", from a parquet table", e);
        }
    }
}
