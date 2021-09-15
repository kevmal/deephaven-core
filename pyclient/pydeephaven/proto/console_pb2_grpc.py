# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

from pydeephaven.proto import console_pb2 as deephaven_dot_proto_dot_console__pb2


class ConsoleServiceStub(object):
    """
    Console interaction service
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.GetConsoleTypes = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/GetConsoleTypes',
                request_serializer=deephaven_dot_proto_dot_console__pb2.GetConsoleTypesRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.GetConsoleTypesResponse.FromString,
                )
        self.StartConsole = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/StartConsole',
                request_serializer=deephaven_dot_proto_dot_console__pb2.StartConsoleRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.StartConsoleResponse.FromString,
                )
        self.SubscribeToLogs = channel.unary_stream(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/SubscribeToLogs',
                request_serializer=deephaven_dot_proto_dot_console__pb2.LogSubscriptionRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.LogSubscriptionData.FromString,
                )
        self.ExecuteCommand = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/ExecuteCommand',
                request_serializer=deephaven_dot_proto_dot_console__pb2.ExecuteCommandRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.ExecuteCommandResponse.FromString,
                )
        self.CancelCommand = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/CancelCommand',
                request_serializer=deephaven_dot_proto_dot_console__pb2.CancelCommandRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.CancelCommandResponse.FromString,
                )
        self.BindTableToVariable = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/BindTableToVariable',
                request_serializer=deephaven_dot_proto_dot_console__pb2.BindTableToVariableRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.BindTableToVariableResponse.FromString,
                )
        self.AutoCompleteStream = channel.stream_stream(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/AutoCompleteStream',
                request_serializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.FromString,
                )
        self.OpenAutoCompleteStream = channel.unary_stream(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/OpenAutoCompleteStream',
                request_serializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.FromString,
                )
        self.NextAutoCompleteStream = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/NextAutoCompleteStream',
                request_serializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.BrowserNextResponse.FromString,
                )
        self.FetchFigure = channel.unary_unary(
                '/io.deephaven.proto.backplane.script.grpc.ConsoleService/FetchFigure',
                request_serializer=deephaven_dot_proto_dot_console__pb2.FetchFigureRequest.SerializeToString,
                response_deserializer=deephaven_dot_proto_dot_console__pb2.FetchFigureResponse.FromString,
                )


class ConsoleServiceServicer(object):
    """
    Console interaction service
    """

    def GetConsoleTypes(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def StartConsole(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def SubscribeToLogs(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ExecuteCommand(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def CancelCommand(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def BindTableToVariable(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def AutoCompleteStream(self, request_iterator, context):
        """
        Starts a stream for autocomplete on the current session. More than one console,
        more than one document can be edited at a time using this, and they can separately
        be closed as well. A given document should only be edited within one stream at a
        time.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def OpenAutoCompleteStream(self, request, context):
        """
        Half of the browser-based (browser's can't do bidirectional streams without websockets)
        implementation for AutoCompleteStream.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def NextAutoCompleteStream(self, request, context):
        """
        Other half of the browser-based implementation for AutoCompleteStream.
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def FetchFigure(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_ConsoleServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'GetConsoleTypes': grpc.unary_unary_rpc_method_handler(
                    servicer.GetConsoleTypes,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.GetConsoleTypesRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.GetConsoleTypesResponse.SerializeToString,
            ),
            'StartConsole': grpc.unary_unary_rpc_method_handler(
                    servicer.StartConsole,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.StartConsoleRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.StartConsoleResponse.SerializeToString,
            ),
            'SubscribeToLogs': grpc.unary_stream_rpc_method_handler(
                    servicer.SubscribeToLogs,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.LogSubscriptionRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.LogSubscriptionData.SerializeToString,
            ),
            'ExecuteCommand': grpc.unary_unary_rpc_method_handler(
                    servicer.ExecuteCommand,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.ExecuteCommandRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.ExecuteCommandResponse.SerializeToString,
            ),
            'CancelCommand': grpc.unary_unary_rpc_method_handler(
                    servicer.CancelCommand,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.CancelCommandRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.CancelCommandResponse.SerializeToString,
            ),
            'BindTableToVariable': grpc.unary_unary_rpc_method_handler(
                    servicer.BindTableToVariable,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.BindTableToVariableRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.BindTableToVariableResponse.SerializeToString,
            ),
            'AutoCompleteStream': grpc.stream_stream_rpc_method_handler(
                    servicer.AutoCompleteStream,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.SerializeToString,
            ),
            'OpenAutoCompleteStream': grpc.unary_stream_rpc_method_handler(
                    servicer.OpenAutoCompleteStream,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.SerializeToString,
            ),
            'NextAutoCompleteStream': grpc.unary_unary_rpc_method_handler(
                    servicer.NextAutoCompleteStream,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.BrowserNextResponse.SerializeToString,
            ),
            'FetchFigure': grpc.unary_unary_rpc_method_handler(
                    servicer.FetchFigure,
                    request_deserializer=deephaven_dot_proto_dot_console__pb2.FetchFigureRequest.FromString,
                    response_serializer=deephaven_dot_proto_dot_console__pb2.FetchFigureResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'io.deephaven.proto.backplane.script.grpc.ConsoleService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class ConsoleService(object):
    """
    Console interaction service
    """

    @staticmethod
    def GetConsoleTypes(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/GetConsoleTypes',
            deephaven_dot_proto_dot_console__pb2.GetConsoleTypesRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.GetConsoleTypesResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def StartConsole(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/StartConsole',
            deephaven_dot_proto_dot_console__pb2.StartConsoleRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.StartConsoleResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def SubscribeToLogs(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/SubscribeToLogs',
            deephaven_dot_proto_dot_console__pb2.LogSubscriptionRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.LogSubscriptionData.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def ExecuteCommand(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/ExecuteCommand',
            deephaven_dot_proto_dot_console__pb2.ExecuteCommandRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.ExecuteCommandResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def CancelCommand(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/CancelCommand',
            deephaven_dot_proto_dot_console__pb2.CancelCommandRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.CancelCommandResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def BindTableToVariable(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/BindTableToVariable',
            deephaven_dot_proto_dot_console__pb2.BindTableToVariableRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.BindTableToVariableResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def AutoCompleteStream(request_iterator,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.stream_stream(request_iterator, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/AutoCompleteStream',
            deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def OpenAutoCompleteStream(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/OpenAutoCompleteStream',
            deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.AutoCompleteResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def NextAutoCompleteStream(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/NextAutoCompleteStream',
            deephaven_dot_proto_dot_console__pb2.AutoCompleteRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.BrowserNextResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)

    @staticmethod
    def FetchFigure(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(request, target, '/io.deephaven.proto.backplane.script.grpc.ConsoleService/FetchFigure',
            deephaven_dot_proto_dot_console__pb2.FetchFigureRequest.SerializeToString,
            deephaven_dot_proto_dot_console__pb2.FetchFigureResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)