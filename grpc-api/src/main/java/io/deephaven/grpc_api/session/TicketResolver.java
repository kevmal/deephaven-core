/*
 * Copyright (c) 2016-2021 Deephaven Data Labs and Patent Pending
 */

package io.deephaven.grpc_api.session;

import org.apache.arrow.flight.impl.Flight;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public interface TicketResolver {
    /**
     * @return the single byte prefix used as a route on the ticket
     */
    byte ticketRoute();

    /**
     * The first path entry on a route indicates which resolver to use. The remaining path elements are used to resolve
     * the descriptor.
     *
     * @return the string that will route from flight descriptor to this resolver
     */
    String flightDescriptorRoute();

    /**
     * Resolve a flight ticket to an export object future.
     *
     * @param session the user session context
     * @param ticket (as ByteByffer) the ticket to resolve
     * @param <T> the expected return type of the ticket; this is not validated
     * @return an export object; see {@link SessionState} for lifecycle propagation details
     */
    <T> SessionState.ExportObject<T> resolve(SessionState session, ByteBuffer ticket);

    /**
     * Resolve a flight descriptor to an export object future.
     *
     * @param session the user session context
     * @param descriptor the descriptor to resolve
     * @param <T> the expected return type of the ticket; this is not validated
     * @return an export object; see {@link SessionState} for lifecycle propagation details
     */
    <T> SessionState.ExportObject<T> resolve(SessionState session, Flight.FlightDescriptor descriptor);

    /**
     * Publish a new result as a flight ticket to an export object future.
     *
     * The user must call {@link SessionState.ExportBuilder#submit} to publish the result value.
     *
     * @param session the user session context
     * @param ticket (as ByteByffer) the ticket to publish to
     * @param <T> the type of the result the export will publish
     * @return an export object; see {@link SessionState} for lifecycle propagation details
     */
    <T> SessionState.ExportBuilder<T> publish(SessionState session, ByteBuffer ticket);

    /**
     * Publish a new result as a flight descriptor to an export object future.
     *
     * The user must call {@link SessionState.ExportBuilder#submit} to publish the result value.
     *
     * @param session the user session context
     * @param descriptor (as Flight.Descriptor) the descriptor to publish to
     * @param <T> the type of the result the export will publish
     * @return an export object; see {@link SessionState} for lifecycle propagation details
     */
    <T> SessionState.ExportBuilder<T> publish(SessionState session, Flight.FlightDescriptor descriptor);

    /**
     * Retrieve a FlightInfo for a given FlightDescriptor.
     *
     * @param descriptor the flight descriptor to retrieve a ticket for
     * @return a FlightInfo describing this flight
     */
    Flight.FlightInfo flightInfoFor(Flight.FlightDescriptor descriptor);

    /**
     * Create a human readable string to identify this ticket.
     *
     * @param ticket the ticket to parse
     * @return a string that is good for log/error messages
     * @apiNote There is not a {@link Flight.FlightDescriptor} equivalent as the path must already be displayable.
     */
    String getLogNameFor(ByteBuffer ticket);

    /**
     * This invokes the provided visitor for each valid flight descriptor this ticket resolver exposes via flight.
     *
     * @param session optional session that the resolver can use to filter which flights a visitor sees
     * @param visitor the callback to invoke per descriptor path
     */
    void forAllFlightInfo(@Nullable SessionState session, Consumer<Flight.FlightInfo> visitor);
}