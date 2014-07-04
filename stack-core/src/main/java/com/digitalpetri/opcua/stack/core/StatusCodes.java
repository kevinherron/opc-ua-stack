package com.digitalpetri.opcua.stack.core;


import com.digitalpetri.opcua.stack.core.util.annotations.Description;

@SuppressWarnings("unused")
public class StatusCodes {

    @Description("An unexpected error occurred.")
    public final static long Bad_UnexpectedError = 0x80010000;

    @Description("An internal error occurred as a result of a programming or configuration error.")
    public final static long Bad_InternalError = 0x80020000;

    @Description("Not enough memory to complete the operation.")
    public final static long Bad_OutOfMemory = 0x80030000;

    @Description("An operating system resource is not available.")
    public final static long Bad_ResourceUnavailable = 0x80040000;

    @Description("A low level communication error occurred.")
    public final static long Bad_CommunicationError = 0x80050000;

    @Description("Encoding halted because of invalid data in the objects being serialized.")
    public final static long Bad_EncodingError = 0x80060000;

    @Description("Decoding halted because of invalid data in the stream.")
    public final static long Bad_DecodingError = 0x80070000;

    @Description("The message encoding/decoding limits imposed by the stack have been exceeded.")
    public final static long Bad_EncodingLimitsExceeded = 0x80080000;

    @Description("An unrecognized response was received from the server.")
    public final static long Bad_UnknownResponse = 0x80090000;

    @Description("The operation timed out.")
    public final static long Bad_Timeout = 0x800A0000;

    @Description("The server does not support the requested service.")
    public final static long Bad_ServiceUnsupported = 0x800B0000;

    @Description("The operation was cancelled because the application is shutting down.")
    public final static long Bad_Shutdown = 0x800C0000;

    @Description("The operation could not complete because the client is not connected to the server.")
    public final static long Bad_ServerNotConnected = 0x800D0000;

    @Description("The server has stopped and cannot process any requests.")
    public final static long Bad_ServerHalted = 0x800E0000;

    @Description("There was nothing to do because the client passed a list of operations with no elements.")
    public final static long Bad_NothingToDo = 0x800F0000;

    @Description("The request could not be processed because it specified too many operations.")
    public final static long Bad_TooManyOperations = 0x80100000;

    @Description("The extension object cannot be (de)serialized because the data type id is not recognized.")
    public final static long Bad_DataTypeIdUnknown = 0x80110000;

    @Description("The certificate provided as a parameter is not valid.")
    public final static long Bad_CertificateInvalid = 0x80120000;

    @Description("An error occurred verifying security.")
    public final static long Bad_SecurityChecksFailed = 0x80130000;

    @Description("The Certificate has expired or is not yet valid.")
    public final static long Bad_CertificateTimeInvalid = 0x80140000;

    @Description("An Issuer Certificate has expired or is not yet valid.")
    public final static long Bad_CertificateIssuerTimeInvalid = 0x80150000;

    @Description("The HostName used to connect to a Server does not match a HostName in the Certificate.")
    public final static long Bad_CertificateHostNameInvalid = 0x80160000;

    @Description("The URI specified in the ApplicationDescription does not match the URI in the Certificate.")
    public final static long Bad_CertificateUriInvalid = 0x80170000;

    @Description("The Certificate may not be used for the requested operation.")
    public final static long Bad_CertificateUseNotAllowed = 0x80180000;

    @Description("The Issuer Certificate may not be used for the requested operation.")
    public final static long Bad_CertificateIssuerUseNotAllowed = 0x80190000;

    @Description("The Certificate is not trusted.")
    public final static long Bad_CertificateUntrusted = 0x801A0000;

    @Description("It was not possible to determine if the Certificate has been revoked.")
    public final static long Bad_CertificateRevocationUnknown = 0x801B0000;

    @Description("It was not possible to determine if the Issuer Certificate has been revoked.")
    public final static long Bad_CertificateIssuerRevocationUnknown = 0x801C0000;

    @Description("The Certificate has been revoked.")
    public final static long Bad_CertificateRevoked = 0x801D0000;

    @Description("The Issuer Certificate has been revoked.")
    public final static long Bad_CertificateIssuerRevoked = 0x801E0000;

    @Description("User does not have permission to perform the requested operation.")
    public final static long Bad_UserAccessDenied = 0x801F0000;

    @Description("The user identity token is not valid.")
    public final static long Bad_IdentityTokenInvalid = 0x80200000;

    @Description("The user identity token is valid but the server has rejected it.")
    public final static long Bad_IdentityTokenRejected = 0x80210000;

    @Description("The specified secure channel is no longer valid.")
    public final static long Bad_SecureChannelIdInvalid = 0x80220000;

    @Description("The timestamp is outside the range allowed by the server.")
    public final static long Bad_InvalidTimestamp = 0x80230000;

    @Description("The nonce does appear to be not a random value or it is not the correct length.")
    public final static long Bad_NonceInvalid = 0x80240000;

    @Description("The session id is not valid.")
    public final static long Bad_SessionIdInvalid = 0x80250000;

    @Description("The session was closed by the client.")
    public final static long Bad_SessionClosed = 0x80260000;

    @Description("The session cannot be used because ActivateSession has not been called.")
    public final static long Bad_SessionNotActivated = 0x80270000;

    @Description("The subscription id is not valid.")
    public final static long Bad_SubscriptionIdInvalid = 0x80280000;

    @Description("The header for the request is missing or invalid.")
    public final static long Bad_RequestHeaderInvalid = 0x802A0000;

    @Description("The timestamps to return parameter is invalid.")
    public final static long Bad_TimestampsToReturnInvalid = 0x802B0000;

    @Description("The request was cancelled by the client.")
    public final static long Bad_RequestCancelledByClient = 0x802C0000;

    @Description("The subscription was transferred to another session.")
    public final static long Good_SubscriptionTransferred = 0x002D0000;

    @Description("The processing will complete asynchronously.")
    public final static long Good_CompletesAsynchronously = 0x002E0000;

    @Description("Sampling has slowed down due to resource limitations.")
    public final static long Good_Overload = 0x002F0000;

    @Description("The value written was accepted but was clamped.")
    public final static long Good_Clamped = 0x00300000;

    @Description("Communication with the data source is defined, but not established, and there is no last known value available.")
    public final static long Bad_NoCommunication = 0x80310000;

    @Description("Waiting for the server to obtain values from the underlying data source.")
    public final static long Bad_WaitingForInitialData = 0x80320000;

    @Description("The syntax of the node id is not valid.")
    public final static long Bad_NodeIdInvalid = 0x80330000;

    @Description("The node id refers to a node that does not exist in the server address space.")
    public final static long Bad_NodeIdUnknown = 0x80340000;

    @Description("The attribute is not supported for the specified Node.")
    public final static long Bad_AttributeIdInvalid = 0x80350000;

    @Description("The syntax of the index range parameter is invalid.")
    public final static long Bad_IndexRangeInvalid = 0x80360000;

    @Description("No data exists within the range of indexes specified.")
    public final static long Bad_IndexRangeNoData = 0x80370000;

    @Description("The data encoding is invalid.")
    public final static long Bad_DataEncodingInvalid = 0x80380000;

    @Description("The server does not support the requested data encoding for the node.")
    public final static long Bad_DataEncodingUnsupported = 0x80390000;

    @Description("The access level does not allow reading or subscribing to the Node.")
    public final static long Bad_NotReadable = 0x803A0000;

    @Description("The access level does not allow writing to the Node.")
    public final static long Bad_NotWritable = 0x803B0000;

    @Description("The value was out of range.")
    public final static long Bad_OutOfRange = 0x803C0000;

    @Description("The requested operation is not supported.")
    public final static long Bad_NotSupported = 0x803D0000;

    @Description("A requested item was not found or a search operation ended without success.")
    public final static long Bad_NotFound = 0x803E0000;

    @Description("The object cannot be used because it has been deleted.")
    public final static long Bad_ObjectDeleted = 0x803F0000;

    @Description("Requested operation is not implemented.")
    public final static long Bad_NotImplemented = 0x80400000;

    @Description("The monitoring mode is invalid.")
    public final static long Bad_MonitoringModeInvalid = 0x80410000;

    @Description("The monitoring item id does not refer to a valid monitored item.")
    public final static long Bad_MonitoredItemIdInvalid = 0x80420000;

    @Description("The monitored item filter parameter is not valid.")
    public final static long Bad_MonitoredItemFilterInvalid = 0x80430000;

    @Description("The server does not support the requested monitored item filter.")
    public final static long Bad_MonitoredItemFilterUnsupported = 0x80440000;

    @Description("A monitoring filter cannot be used in combination with the attribute specified.")
    public final static long Bad_FilterNotAllowed = 0x80450000;

    @Description("A mandatory structured parameter was missing or null.")
    public final static long Bad_StructureMissing = 0x80460000;

    @Description("The event filter is not valid.")
    public final static long Bad_EventFilterInvalid = 0x80470000;

    @Description("The content filter is not valid.")
    public final static long Bad_ContentFilterInvalid = 0x80480000;

    @Description("The operand used in a content filter is not valid.")
    public final static long Bad_FilterOperandInvalid = 0x80490000;

    @Description("The continuation point provide is longer valid.")
    public final static long Bad_ContinuationPointInvalid = 0x804A0000;

    @Description("The operation could not be processed because all continuation points have been allocated.")
    public final static long Bad_NoContinuationPoints = 0x804B0000;

    @Description("The operation could not be processed because all continuation points have been allocated.")
    public final static long Bad_ReferenceTypeIdInvalid = 0x804C0000;

    @Description("The browse direction is not valid.")
    public final static long Bad_BrowseDirectionInvalid = 0x804D0000;

    @Description("The node is not part of the view.")
    public final static long Bad_NodeNotInView = 0x804E0000;

    @Description("The ServerUri is not a valid URI.")
    public final static long Bad_ServerUriInvalid = 0x804F0000;

    @Description("No ServerName was specified.")
    public final static long Bad_ServerNameMissing = 0x80500000;

    @Description("No DiscoveryUrl was specified.")
    public final static long Bad_DiscoveryUrlMissing = 0x80510000;

    @Description("The semaphore file specified by the client is not valid.")
    public final static long Bad_SemaphoreFileMissing = 0x80520000;

    @Description("The security token request type is not valid.")
    public final static long Bad_RequestTypeInvalid = 0x80530000;

    @Description("The security mode does not meet the requirements set by the Server.")
    public final static long Bad_SecurityModeRejected = 0x80540000;

    @Description("The security policy does not meet the requirements set by the Server.")
    public final static long Bad_SecurityPolicyRejected = 0x80550000;

    @Description("The server has reached its maximum number of sessions.")
    public final static long Bad_TooManySessions = 0x80560000;

    @Description("The user token signature is missing or invalid.")
    public final static long Bad_UserSignatureInvalid = 0x80570000;

    @Description("The signature generated with the client certificate is missing or invalid.")
    public final static long Bad_ApplicationSignatureInvalid = 0x80580000;

    @Description("The client did not provide at least one software certificate that is valid and meets the profile requirements for the server.")
    public final static long Bad_NoValidCertificates = 0x80590000;

    @Description("The request was cancelled by the client with the Cancel service.")
    public final static long Bad_RequestCancelledByRequest = 0x805A0000;

    @Description("The parent node id does not to refer to a valid node.")
    public final static long Bad_ParentNodeIdInvalid = 0x805B0000;

    @Description("The reference could not be created because it violates constraints imposed by the data model.")
    public final static long Bad_ReferenceNotAllowed = 0x805C0000;

    @Description("The requested node id was reject because it was either invalid or server does not allow node ids to be specified by the client.")
    public final static long Bad_NodeIdRejected = 0x805D0000;

    @Description("The requested node id is already used by another node.")
    public final static long Bad_NodeIdExists = 0x805E0000;

    @Description("The node class is not valid.")
    public final static long Bad_NodeClassInvalid = 0x805F0000;

    @Description("The browse name is invalid.")
    public final static long Bad_BrowseNameInvalid = 0x80600000;

    @Description("The browse name is not unique among nodes that share the same relationship with the parent.")
    public final static long Bad_BrowseNameDuplicated = 0x80610000;

    @Description("The node attributes are not valid for the node class.")
    public final static long Bad_NodeAttributesInvalid = 0x80620000;

    @Description("The type definition node id does not reference an appropriate type node.")
    public final static long Bad_TypeDefinitionInvalid = 0x80630000;

    @Description("The source node id does not reference a valid node.")
    public final static long Bad_SourceNodeIdInvalid = 0x80640000;

    @Description("The target node id does not reference a valid node.")
    public final static long Bad_TargetNodeIdInvalid = 0x80650000;

    @Description("The reference type between the nodes is already defined.")
    public final static long Bad_DuplicateReferenceNotAllowed = 0x80660000;

    @Description("The server does not allow this type of self reference on this node.")
    public final static long Bad_InvalidSelfReference = 0x80670000;

    @Description("The reference type is not valid for a reference to a remote server.")
    public final static long Bad_ReferenceLocalOnly = 0x80680000;

    @Description("The server will not allow the node to be deleted.")
    public final static long Bad_NoDeleteRights = 0x80690000;

    @Description("The server index is not valid.")
    public final static long Bad_ServerIndexInvalid = 0x806A0000;

    @Description("The view id does not refer to a valid view node.")
    public final static long Bad_ViewIdUnknown = 0x806B0000;

    @Description("One of the references to follow in the relative path references to a node in the address space in another server.")
    public final static long Uncertain_ReferenceOutOfServer = 0x406C0000;

    @Description("The requested operation has too many matches to return.")
    public final static long Bad_TooManyMatches = 0x806D0000;

    @Description("The requested operation requires too many resources in the server.")
    public final static long Bad_QueryTooComplex = 0x806E0000;

    @Description("The requested operation has no match to return.")
    public final static long Bad_NoMatch = 0x806F0000;

    @Description("The max age parameter is invalid.")
    public final static long Bad_MaxAgeInvalid = 0x80700000;

    @Description("The history details parameter is not valid.")
    public final static long Bad_HistoryOperationInvalid = 0x80710000;

    @Description("The server does not support the requested operation.")
    public final static long Bad_HistoryOperationUnsupported = 0x80720000;

    @Description("The server not does support writing the combination of value, status and timestamps provided.")
    public final static long Bad_WriteNotSupported = 0x80730000;

    @Description("The value supplied for the attribute is not of the same type as the attribute's value.")
    public final static long Bad_TypeMismatch = 0x80740000;

    @Description("The method id does not refer to a method for the specified object.")
    public final static long Bad_MethodInvalid = 0x80750000;

    @Description("The client did not specify all of the input arguments for the method.")
    public final static long Bad_ArgumentsMissing = 0x80760000;

    @Description("The server has reached its  maximum number of subscriptions.")
    public final static long Bad_TooManySubscriptions = 0x80770000;

    @Description("The server has reached the maximum number of queued publish requests.")
    public final static long Bad_TooManyPublishRequests = 0x80780000;

    @Description("There is no subscription available for this session.")
    public final static long Bad_NoSubscription = 0x80790000;

    @Description("The sequence number is unknown to the server.")
    public final static long Bad_SequenceNumberUnknown = 0x807A0000;

    @Description("The requested notification message is no longer available.")
    public final static long Bad_MessageNotAvailable = 0x807B0000;

    @Description("The Client of the current Session does not support one or more Profiles that are necessary for the Subscription.")
    public final static long Bad_InsufficientClientProfile = 0x807C0000;

    @Description("The server cannot process the request because it is too busy.")
    public final static long Bad_TcpServerTooBusy = 0x807D0000;

    @Description("The type of the message specified in the header invalid.")
    public final static long Bad_TcpMessageTypeInvalid = 0x807E0000;

    @Description("The SecureChannelId and/or TokenId are not currently in use.")
    public final static long Bad_TcpSecureChannelUnknown = 0x807F0000;

    @Description("The size of the message specified in the header is too large.")
    public final static long Bad_TcpMessageTooLarge = 0x80800000;

    @Description("There are not enough resources to process the request.")
    public final static long Bad_TcpNotEnoughResources = 0x80810000;

    @Description("An internal error occurred.")
    public final static long Bad_TcpInternalError = 0x80820000;

    @Description("The Server does not recognize the QueryString specified.")
    public final static long Bad_TcpEndpointUrlInvalid = 0x80830000;

    @Description("The request could not be sent because of a network interruption.")
    public final static long Bad_RequestInterrupted = 0x80840000;

    @Description("Timeout occurred while processing the request.")
    public final static long Bad_RequestTimeout = 0x80850000;

    @Description("The secure channel has been closed.")
    public final static long Bad_SecureChannelClosed = 0x80860000;

    @Description("The token has expired or is not recognized.")
    public final static long Bad_SecureChannelTokenUnknown = 0x80870000;

    @Description("The sequence number is not valid.")
    public final static long Bad_SequenceNumberInvalid = 0x80880000;

    @Description("There is a problem with the configuration that affects the usefulness of the value.")
    public final static long Bad_ConfigurationError = 0x80890000;

    @Description("The variable should receive its value from another variable, but has never been configured to do so.")
    public final static long Bad_NotConnected = 0x808A0000;

    @Description("There has been a failure in the device/data source that generates the value that has affected the value.")
    public final static long Bad_DeviceFailure = 0x808B0000;

    @Description("There has been a failure in the sensor from which the value is derived by the device/data source.")
    public final static long Bad_SensorFailure = 0x808C0000;

    @Description("The source of the data is not operational.")
    public final static long Bad_OutOfService = 0x808D0000;

    @Description("The deadband filter is not valid.")
    public final static long Bad_DeadbandFilterInvalid = 0x808E0000;

    @Description("Communication to the data source has failed. The variable value is the last value that had a good quality.")
    public final static long Uncertain_NoCommunicationLastUsableValue = 0x408F0000;

    @Description("Whatever was updating this value has stopped doing so.")
    public final static long Uncertain_LastUsableValue = 0x40900000;

    @Description("The value is an operational value that was manually overwritten.")
    public final static long Uncertain_SubstituteValue = 0x40910000;

    @Description("The value is an initial value for a variable that normally receives its value from another variable.")
    public final static long Uncertain_InitialValue = 0x40920000;

    @Description("The value is at one of the sensor limits.")
    public final static long Uncertain_SensorNotAccurate = 0x40930000;

    @Description("The value is outside of the range of values defined for this parameter.")
    public final static long Uncertain_EngineeringUnitsExceeded = 0x40940000;

    @Description("The value is derived from multiple sources and has less than the required number of Good sources.")
    public final static long Uncertain_SubNormal = 0x40950000;

    @Description("The value has been overridden.")
    public final static long Good_LocalOverride = 0x00960000;

    @Description("This Condition refresh failed, a Condition refresh operation is already in progress.")
    public final static long Bad_RefreshInProgress = 0x80970000;

    @Description("This condition has already been disabled.")
    public final static long Bad_ConditionAlreadyDisabled = 0x80980000;

    @Description("Property not available, this condition is disabled.")
    public final static long Bad_ConditionDisabled = 0x80990000;

    @Description("The specified event id is not recognized.")
    public final static long Bad_EventIdUnknown = 0x809A0000;

    @Description("No data exists for the requested time range or event filter.")
    public final static long Bad_NoData = 0x809B0000;

    @Description("Data is missing due to collection started/stopped/lost.")
    public final static long Bad_DataLost = 0x809D0000;

    @Description("Expected data is unavailable for the requested time range due to an un-mounted volume, an off-line archive or tape, or similar reason for temporary unavailability.")
    public final static long Bad_DataUnavailable = 0x809E0000;

    @Description("The data or event was not successfully inserted because a matching entry exists.")
    public final static long Bad_EntryExists = 0x809F0000;

    @Description("The data or event was not successfully updated because no matching entry exists.")
    public final static long Bad_NoEntryExists = 0x80A00000;

    @Description("The client requested history using a timestamp format the server does not support (i.e requested ServerTimestamp when server only supports SourceTimestamp).")
    public final static long Bad_TimestampNotSupported = 0x80A10000;

    @Description("The data or event was successfully inserted into the historical database.")
    public final static long Good_EntryInserted = 0x00A20000;

    @Description("The data or event field was successfully replaced in the historical database.")
    public final static long Good_EntryReplaced = 0x00A30000;

    @Description("The value is derived from multiple values and has less than the required number of Good values.")
    public final static long Uncertain_DataSubNormal = 0x40A40000;

    @Description("No data exists for the requested time range or event filter.")
    public final static long Good_NoData = 0x00A50000;

    @Description("The data or event field was successfully replaced in the historical database.")
    public final static long Good_MoreData = 0x00A60000;

    @Description("The communication layer has raised an event.")
    public final static long Good_CommunicationEvent = 0x00A70000;

    @Description("The system is shutting down.")
    public final static long Good_ShutdownEvent = 0x00A80000;

    @Description("The operation is not finished and needs to be called again.")
    public final static long Good_CallAgain = 0x00A90000;

    @Description("A non-critical timeout occurred.")
    public final static long Good_NonCriticalTimeout = 0x00AA0000;

    @Description("One or more arguments are invalid.")
    public final static long Bad_InvalidArgument = 0x80AB0000;

    @Description("Could not establish a network connection to remote server.")
    public final static long Bad_ConnectionRejected = 0x80AC0000;

    @Description("The server has disconnected from the client.")
    public final static long Bad_Disconnect = 0x80AD0000;

    @Description("The network connection has been closed.")
    public final static long Bad_ConnectionClosed = 0x80AE0000;

    @Description("The operation cannot be completed because the object is closed, uninitialized or in some other invalid state.")
    public final static long Bad_InvalidState = 0x80AF0000;

    @Description("Cannot move beyond end of the stream.")
    public final static long Bad_EndOfStream = 0x80B00000;

    @Description("No data is currently available for reading from a non-blocking stream.")
    public final static long Bad_NoDataAvailable = 0x80B10000;

    @Description("The asynchronous operation is waiting for a response.")
    public final static long Bad_WaitingForResponse = 0x80B20000;

    @Description("The asynchronous operation was abandoned by the caller.")
    public final static long Bad_OperationAbandoned = 0x80B30000;

    @Description("The stream did not return all data requested (possibly because it is a non-blocking stream).")
    public final static long Bad_ExpectedStreamToBlock = 0x80B40000;

    @Description("Non blocking behaviour is required and the operation would block.")
    public final static long Bad_WouldBlock = 0x80B50000;

    @Description("A value had an invalid syntax.")
    public final static long Bad_SyntaxError = 0x80B60000;

    @Description("The operation could not be finished because all available connections are in use.")
    public final static long Bad_MaxConnectionsReached = 0x80B70000;

    @Description("The request message size exceeds limits set by the server.")
    public final static long Bad_RequestTooLarge = 0x80B80000;

    @Description("The response message size exceeds limits set by the client.")
    public final static long Bad_ResponseTooLarge = 0x80B90000;

    @Description("The server should have followed a reference to a node in a remote server but did not. The result set may be incomplete.")
    public final static long Good_ResultsMayBeIncomplete = 0x00BA0000;

    @Description("The event cannot be acknowledged.")
    public final static long Bad_EventNotAcknowledgeable = 0x80BB0000;

    @Description("The server was not able to delete all target references.")
    public final static long Uncertain_ReferenceNotDeleted = 0x40BC0000;

    @Description("The defined timestamp to return was invalid.")
    public final static long Bad_InvalidTimestampArgument = 0x80BD0000;

    @Description("The applications do not have compatible protocol versions.")
    public final static long Bad_ProtocolVersionUnsupported = 0x80BE0000;

    @Description("The sub-state machine is not currently active.")
    public final static long Bad_StateNotActive = 0x80BF0000;

    @Description("The list of references may not be complete because the underlying system is not available.")
    public final static long Uncertain_NotAllNodesAvailable = 0x40C00000;

    @Description("An unrecognized operator was provided in a filter.")
    public final static long Bad_FilterOperatorInvalid = 0x80C10000;

    @Description("A valid operator was provided, but the server does not provide support for this filter operator.")
    public final static long Bad_FilterOperatorUnsupported = 0x80C20000;

    @Description("The number of operands provided for the filter operator was less then expected for the operand provided.")
    public final static long Bad_FilterOperandCountMismatch = 0x80C30000;

    @Description("The referenced element is not a valid element in the content filter.")
    public final static long Bad_FilterElementInvalid = 0x80C40000;

    @Description("The referenced literal is not a valid value.")
    public final static long Bad_FilterLiteralInvalid = 0x80C50000;

    @Description("The Server does not support changing the user identity assigned to the session.")
    public final static long Bad_IdentityChangeNotSupported = 0x80C60000;

    @Description("The provided NodeId was not a type definition NodeId.")
    public final static long Bad_NotTypeDefinition = 0x80C80000;

    @Description("The view timestamp is not available or not supported.")
    public final static long Bad_ViewTimestampInvalid = 0x80C90000;

    @Description("The view parameters are not consistent with each other.")
    public final static long Bad_ViewParameterMismatch = 0x80CA0000;

    @Description("The view version is not available or not supported.")
    public final static long Bad_ViewVersionInvalid = 0x80CB0000;

    @Description("This condition has already been enabled.")
    public final static long Bad_ConditionAlreadyEnabled = 0x80CC0000;

    @Description("The dialog condition is not active.")
    public final static long Bad_DialogNotActive = 0x80CD0000;

    @Description("The response is not valid for the dialog.")
    public final static long Bad_DialogResponseInvalid = 0x80CE0000;

    @Description("The condition branch has already been acknowledged.")
    public final static long Bad_ConditionBranchAlreadyAcked = 0x80CF0000;

    @Description("The condition branch has already been confirmed.")
    public final static long Bad_ConditionBranchAlreadyConfirmed = 0x80D00000;

    @Description("The condition has already been shelved.")
    public final static long Bad_ConditionAlreadyShelved = 0x80D10000;

    @Description("The condition is not currently shelved.")
    public final static long Bad_ConditionNotShelved = 0x80D20000;

    @Description("The shelving time not within an acceptable range.")
    public final static long Bad_ShelvingTimeOutOfRange = 0x80D30000;

    @Description("The requested number of Aggregates does not match the requested number of NodeIds.")
    public final static long Bad_AggregateListMismatch = 0x80D40000;

    @Description("The requested Aggregate is not support by the server.")
    public final static long Bad_AggregateNotSupported = 0x80D50000;

    @Description("The aggregate value could not be derived due to invalid data inputs.")
    public final static long Bad_AggregateInvalidInputs = 0x80D60000;

    @Description("No data found to provide upper or lower bound value.")
    public final static long Bad_BoundNotFound = 0x80D70000;

    @Description("The server cannot retrieve a bound for the variable.")
    public final static long Bad_BoundNotSupported = 0x80D80000;

    @Description("The request specifies fields which are not valid for the EventType or cannot be saved by the historian.")
    public final static long Good_DataIgnored = 0x00D90000;

    @Description("The aggregate configuration is not valid for specified node.")
    public final static long Bad_AggregateConfigurationRejected = 0x80DA0000;

    @Description("The request could not be processed because there are too many monitored items in the subscription.")
    public final static long Bad_TooManyMonitoredItems = 0x80DB0000;

}
