package io.deephaven.client.impl;

import io.deephaven.api.ColumnName;
import io.deephaven.api.Strings;
import io.deephaven.api.agg.Pair;
import io.deephaven.api.updateby.BadDataBehavior;
import io.deephaven.api.updateby.ColumnUpdateOperation;
import io.deephaven.api.updateby.OperationControl;
import io.deephaven.api.updateby.UpdateByControl;
import io.deephaven.api.updateby.UpdateByOperation;
import io.deephaven.api.updateby.spec.*;
import io.deephaven.proto.backplane.grpc.UpdateByEmaTimescale;
import io.deephaven.proto.backplane.grpc.UpdateByRequest;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByCumulativeMax;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByCumulativeMin;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByCumulativeProduct;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByCumulativeSum;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByEma;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByEma.UpdateByEmaOptions;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOperation.UpdateByColumn.UpdateBySpec.UpdateByFill;
import io.deephaven.proto.backplane.grpc.UpdateByRequest.UpdateByOptions;
import io.deephaven.qst.table.UpdateByTable;

import java.math.MathContext;
import java.math.RoundingMode;

class UpdateByBuilder {

    public static UpdateByRequest.Builder adapt(UpdateByTable updateByTable) {
        UpdateByRequest.Builder builder = UpdateByRequest.newBuilder();
        updateByTable.control().map(UpdateByBuilder::adapt).ifPresent(builder::setOptions);
        for (UpdateByOperation operation : updateByTable.operations()) {
            builder.addOperations(adapt(operation));
        }
        for (ColumnName groupByColumn : updateByTable.groupByColumns()) {
            builder.addGroupByColumns(groupByColumn.name());
        }
        return builder;
    }

    private enum OperationVisitor implements UpdateByOperation.Visitor<UpdateByRequest.UpdateByOperation> {
        INSTANCE;

        @Override
        public UpdateByRequest.UpdateByOperation visit(ColumnUpdateOperation clause) {
            return UpdateByRequest.UpdateByOperation.newBuilder().setColumn(adapt(clause)).build();
        }
    }

    static UpdateByRequest.UpdateByOperation adapt(UpdateByOperation clause) {
        return clause.walk(OperationVisitor.INSTANCE);
    }

    private static UpdateByColumn adapt(ColumnUpdateOperation columnUpdate) {
        UpdateByColumn.Builder builder = UpdateByColumn.newBuilder()
                .setSpec(adapt(columnUpdate.spec()));
        for (Pair pair : columnUpdate.columns()) {
            builder.addMatchPairs(Strings.of(pair));
        }
        return builder.build();
    }

    private enum SpecVisitor implements UpdateBySpec.Visitor<UpdateByColumn.UpdateBySpec> {
        INSTANCE;

        private static io.deephaven.proto.backplane.grpc.BadDataBehavior adapt(BadDataBehavior b) {
            switch (b) {
                case RESET:
                    return io.deephaven.proto.backplane.grpc.BadDataBehavior.RESET;
                case SKIP:
                    return io.deephaven.proto.backplane.grpc.BadDataBehavior.SKIP;
                case THROW:
                    return io.deephaven.proto.backplane.grpc.BadDataBehavior.THROW;
                case POISON:
                    return io.deephaven.proto.backplane.grpc.BadDataBehavior.POISON;
                default:
                    throw new IllegalArgumentException("Unexpected BadDataBehavior: " + b);
            }
        }

        private static UpdateByEmaOptions adapt(OperationControl control) {
            UpdateByEmaOptions.Builder builder = UpdateByEmaOptions.newBuilder();
            control.onNullValue().map(SpecVisitor::adapt).ifPresent(builder::setOnNullValue);
            control.onNanValue().map(SpecVisitor::adapt).ifPresent(builder::setOnNanValue);
            control.onNullTime().map(SpecVisitor::adapt).ifPresent(builder::setOnNullTime);
            control.onNegativeDeltaTime().map(SpecVisitor::adapt).ifPresent(builder::setOnNegativeDeltaTime);
            control.onZeroDeltaTime().map(SpecVisitor::adapt).ifPresent(builder::setOnZeroDeltaTime);
            control.bigValueContext().map(UpdateByBuilder::adapt).ifPresent(builder::setBigValueContext);
            return builder.build();
        }

        private static UpdateByEmaTimescale adapt(WindowScale windowScale) {
            if (windowScale.isTimeBased()) {
                return UpdateByEmaTimescale.newBuilder()
                        .setTime(UpdateByEmaTimescale.UpdateByEmaTime.newBuilder()
                                .setColumn(windowScale.timestampCol())
                                .setPeriodNanos(windowScale.timescaleUnits())
                                .build())
                        .build();
            } else {
                return UpdateByEmaTimescale.newBuilder()
                        .setTicks(UpdateByEmaTimescale.UpdateByEmaTicks.newBuilder()
                                .setTicks(windowScale.timescaleUnits())
                                .build())
                        .build();
            }
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(EmaSpec spec) {
            UpdateByEma.Builder builder = UpdateByEma.newBuilder().setTimescale(adapt(spec.timeScale()));
            spec.control().map(SpecVisitor::adapt).ifPresent(builder::setOptions);
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setEma(builder.build())
                    .build();
        }

        // TODO: complete properly (DHC ticket #3666)
        @Override
        public UpdateByColumn.UpdateBySpec visit(EmsSpec spec) {
            throw new UnsupportedOperationException("EmsSpec not added to table.proto");
        }

        // TODO: complete properly (DHC ticket #3666)
        @Override
        public UpdateByColumn.UpdateBySpec visit(EmMinMaxSpec spec) {
            return null;
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(FillBySpec spec) {
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setFill(UpdateByFill.getDefaultInstance())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(CumSumSpec spec) {
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setSum(UpdateByCumulativeSum.getDefaultInstance())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(CumMinMaxSpec spec) {
            if (spec.isMax()) {
                return UpdateByColumn.UpdateBySpec.newBuilder()
                        .setMax(UpdateByCumulativeMax.getDefaultInstance())
                        .build();
            } else {
                return UpdateByColumn.UpdateBySpec.newBuilder()
                        .setMin(UpdateByCumulativeMin.getDefaultInstance())
                        .build();
            }
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(CumProdSpec spec) {
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setProduct(UpdateByCumulativeProduct.getDefaultInstance())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(DeltaSpec spec) {
            return null;
        }

        // TODO: add this correctly to `table.proto` (DHC #3392)
        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingSumSpec rs) {
            final UpdateByColumn.UpdateBySpec.UpdateByRollingSum.Builder builder =
                    UpdateByColumn.UpdateBySpec.UpdateByRollingSum.newBuilder()
                            .setReverseTimescale(adapt(rs.revWindowScale()))
                            .setForwardTimescale(adapt(rs.fwdWindowScale()));
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setRollingSum(builder.build())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingGroupSpec rs) {
            final UpdateByColumn.UpdateBySpec.UpdateByRollingGroup.Builder builder =
                    UpdateByColumn.UpdateBySpec.UpdateByRollingGroup.newBuilder()
                            .setReverseTimescale(adapt(rs.revWindowScale()))
                            .setForwardTimescale(adapt(rs.fwdWindowScale()));
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setRollingGroup(builder.build())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingAvgSpec rs) {
            final UpdateByColumn.UpdateBySpec.UpdateByRollingAvg.Builder builder =
                    UpdateByColumn.UpdateBySpec.UpdateByRollingAvg.newBuilder()
                            .setReverseTimescale(adapt(rs.revWindowScale()))
                            .setForwardTimescale(adapt(rs.fwdWindowScale()));
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setRollingAvg(builder.build())
                    .build();
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingMinMaxSpec rs) {
            if (rs.isMax()) {
                final UpdateByColumn.UpdateBySpec.UpdateByRollingMax.Builder builder =
                        UpdateByColumn.UpdateBySpec.UpdateByRollingMax.newBuilder()
                                .setReverseTimescale(adapt(rs.revWindowScale()))
                                .setForwardTimescale(adapt(rs.fwdWindowScale()));
                return UpdateByColumn.UpdateBySpec.newBuilder()
                        .setRollingMax(builder.build())
                        .build();
            } else {
                final UpdateByColumn.UpdateBySpec.UpdateByRollingMin.Builder builder =
                        UpdateByColumn.UpdateBySpec.UpdateByRollingMin.newBuilder()
                                .setReverseTimescale(adapt(rs.revWindowScale()))
                                .setForwardTimescale(adapt(rs.fwdWindowScale()));
                return UpdateByColumn.UpdateBySpec.newBuilder()
                        .setRollingMin(builder.build())
                        .build();
            }
        }

        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingProductSpec rs) {
            final UpdateByColumn.UpdateBySpec.UpdateByRollingProduct.Builder builder =
                    UpdateByColumn.UpdateBySpec.UpdateByRollingProduct.newBuilder()
                            .setReverseTimescale(adapt(rs.revWindowScale()))
                            .setForwardTimescale(adapt(rs.fwdWindowScale()));
            return UpdateByColumn.UpdateBySpec.newBuilder()
                    .setRollingProduct(builder.build())
                    .build();
        }

        // TODO: add this correctly to `table.proto` (DHC #3392)
        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingCountSpec spec) {
            return null;
        }

        // TODO: add this correctly to `table.proto` (DHC #3392)
        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingStdSpec spec) {
            return null;
        }

        // TODO: add this correctly to `table.proto` (DHC #3392)
        @Override
        public UpdateByColumn.UpdateBySpec visit(RollingWAvgSpec spec) {
            return null;
        }
    }

    static UpdateByColumn.UpdateBySpec adapt(UpdateBySpec spec) {
        return spec.walk(SpecVisitor.INSTANCE);
    }

    static UpdateByOptions adapt(UpdateByControl control) {
        UpdateByOptions.Builder builder = UpdateByOptions.newBuilder();
        final Boolean useRedirection = control.useRedirection();
        if (useRedirection != null) {
            builder.setUseRedirection(useRedirection);
        }
        control.chunkCapacity().ifPresent(builder::setChunkCapacity);
        control.maxStaticSparseMemoryOverhead().ifPresent(builder::setMaxStaticSparseMemoryOverhead);
        control.initialHashTableSize().ifPresent(builder::setInitialHashTableSize);
        control.maximumLoadFactor().ifPresent(builder::setMaximumLoadFactor);
        control.targetLoadFactor().ifPresent(builder::setTargetLoadFactor);
        control.mathContext().map(UpdateByBuilder::adapt).ifPresent(builder::setMathContext);
        return builder.build();
    }

    static io.deephaven.proto.backplane.grpc.MathContext adapt(MathContext mathContext) {
        return io.deephaven.proto.backplane.grpc.MathContext.newBuilder()
                .setPrecision(mathContext.getPrecision())
                .setRoundingMode(adapt(mathContext.getRoundingMode()))
                .build();
    }

    private static io.deephaven.proto.backplane.grpc.MathContext.RoundingMode adapt(RoundingMode roundingMode) {
        switch (roundingMode) {
            case UP:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.UP;
            case DOWN:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.DOWN;
            case CEILING:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.CEILING;
            case FLOOR:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.FLOOR;
            case HALF_UP:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.HALF_UP;
            case HALF_DOWN:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.HALF_DOWN;
            case HALF_EVEN:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.HALF_EVEN;
            case UNNECESSARY:
                return io.deephaven.proto.backplane.grpc.MathContext.RoundingMode.UNNECESSARY;
            default:
                throw new IllegalArgumentException("Unexpected rounding mode: " + roundingMode);
        }
    }
}
