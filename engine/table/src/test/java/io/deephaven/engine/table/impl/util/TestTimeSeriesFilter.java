/**
 * Copyright (c) 2016-2022 Deephaven Data Labs and Patent Pending
 */
package io.deephaven.engine.table.impl.util;

import io.deephaven.engine.table.Table;
import io.deephaven.engine.testutil.ColumnInfo;
import io.deephaven.engine.testutil.generator.DateGenerator;
import io.deephaven.engine.testutil.generator.IntGenerator;
import io.deephaven.engine.updategraph.UpdateGraphProcessor;
import io.deephaven.time.DateTime;
import io.deephaven.engine.util.TableTools;
import io.deephaven.engine.testutil.EvalNugget;
import io.deephaven.engine.testutil.testcase.RefreshingTableTestCase;
import io.deephaven.engine.table.impl.QueryTable;
import io.deephaven.engine.testutil.TstUtils;
import io.deephaven.engine.table.impl.select.TimeSeriesFilter;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static io.deephaven.engine.testutil.TstUtils.getTable;
import static io.deephaven.engine.testutil.TstUtils.initColumnInfos;

public class TestTimeSeriesFilter extends RefreshingTableTestCase {
    public void testSimple() {
        DateTime[] times = new DateTime[10];

        final long startTime = System.currentTimeMillis() - (10 * times.length);
        for (int ii = 0; ii < times.length; ++ii) {
            times[ii] = new DateTime((startTime + (ii * 1000)) * 1000000L);
        }

        Table source = TableTools.newTable(TableTools.col("Timestamp", times));
        TableTools.show(source);

        UnitTestTimeSeriesFilter timeSeriesFilter = new UnitTestTimeSeriesFilter(startTime, "Timestamp", "00:00:05");
        Table filtered = source.where(timeSeriesFilter);

        TableTools.show(filtered);
        assertEquals(10, filtered.size());

        UpdateGraphProcessor.DEFAULT.runWithinUnitTestCycle(() -> {
            timeSeriesFilter.incrementNow(5000);
            timeSeriesFilter.run();
        });

        TableTools.show(filtered);
        assertEquals(10, filtered.size());

        UpdateGraphProcessor.DEFAULT.runWithinUnitTestCycle(() -> {
            timeSeriesFilter.incrementNow(5000);
            timeSeriesFilter.run();
        });

        TableTools.show(filtered);
        assertEquals(5, filtered.size());

        UpdateGraphProcessor.DEFAULT.runWithinUnitTestCycle(() -> {
            timeSeriesFilter.incrementNow(2000);
            timeSeriesFilter.run();
        });

        TableTools.show(filtered);
        assertEquals(3, filtered.size());
    }

    public void testIncremental() throws ParseException {
        Random random = new Random(0);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        ColumnInfo<?, ?>[] columnInfo;
        int size = 100;
        final Date startDate = format.parse("2015-03-23");
        Date endDate = format.parse("2015-03-24");
        final QueryTable table = getTable(size, random, columnInfo = initColumnInfos(new String[] {"Date", "C1"},
                new DateGenerator(startDate, endDate),
                new IntGenerator(1, 100)));

        final UnitTestTimeSeriesFilter unitTestTimeSeriesFilter =
                new UnitTestTimeSeriesFilter(startDate.getTime(), "Date", "01:00:00");
        final ArrayList<WeakReference<UnitTestTimeSeriesFilter>> filtersToRefresh = new ArrayList<>();

        EvalNugget[] en = new EvalNugget[] {
                new EvalNugget() {
                    public Table e() {
                        UnitTestTimeSeriesFilter unitTestTimeSeriesFilter1 =
                                new UnitTestTimeSeriesFilter(unitTestTimeSeriesFilter);
                        filtersToRefresh.add(new WeakReference<>(unitTestTimeSeriesFilter1));
                        return UpdateGraphProcessor.DEFAULT.exclusiveLock()
                                .computeLocked(() -> table.update("Date=new DateTime(Date.getTime() * 1000000L)")
                                        .where(unitTestTimeSeriesFilter1));
                    }
                },
        };


        int updatesPerTick = 3;
        for (int ii = 0; ii < 24 * (updatesPerTick + 1); ++ii) {
            if (ii % (updatesPerTick + 1) > 0) {
                simulateShiftAwareStep(size, random, table, columnInfo, en);
            } else {
                UpdateGraphProcessor.DEFAULT.runWithinUnitTestCycle(() -> {
                    unitTestTimeSeriesFilter.incrementNow(3600 * 1000);

                    final ArrayList<WeakReference<UnitTestTimeSeriesFilter>> collectedRefs = new ArrayList<>();
                    for (WeakReference<UnitTestTimeSeriesFilter> ref : filtersToRefresh) {
                        final UnitTestTimeSeriesFilter refreshFilter = ref.get();
                        if (refreshFilter == null) {
                            collectedRefs.add(ref);
                        } else {
                            refreshFilter.setNow(unitTestTimeSeriesFilter.getNowLong());
                            refreshFilter.run();
                        }
                    }
                    filtersToRefresh.removeAll(collectedRefs);
                });
                TstUtils.validate("time update " + ii, en);
            }
        }
    }

    private static class UnitTestTimeSeriesFilter extends TimeSeriesFilter {
        long now;

        public UnitTestTimeSeriesFilter(long startTime, String timestamp, String period) {
            super(timestamp, period);
            now = startTime;
        }

        public UnitTestTimeSeriesFilter(long startTime, String timestamp, long period) {
            super(timestamp, period);
            now = startTime;
        }

        public UnitTestTimeSeriesFilter(UnitTestTimeSeriesFilter other) {
            this(other.now, other.columnName, other.nanos);
        }

        @Override
        protected long getNowNanos() {
            return now * 1000000L;
        }

        long getNowLong() {
            return now;
        }

        void incrementNow(long increment) {
            now += increment;
        }

        void setNow(long newValue) {
            now = newValue;
        }
    }

}
