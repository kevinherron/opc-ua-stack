/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.channel.messages;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;

public enum MessageType {

	Hello,
	Acknowledge,
	Error,
	OpenSecureChannel,
	CloseSecureChannel,
	SecureMessage;

	private static final int HEL = ('L' << 16) | ('E' << 8) | 'H';
	private static final int ACK = ('K' << 16) | ('C' << 8) | 'A';
	private static final int ERR = ('R' << 16) | ('R' << 8) | 'E';
	private static final int OPN = ('N' << 16) | ('P' << 8) | 'O';
	private static final int CLO = ('O' << 16) | ('L' << 8) | 'C';
	private static final int MSG = ('G' << 16) | ('S' << 8) | 'M';

	public static int toMediumInt(MessageType messageType) throws UaException {
		switch (messageType) {
			case Hello:
				return HEL;
			case Acknowledge:
				return ACK;
			case Error:
				return ERR;
			case OpenSecureChannel:
				return OPN;
			case CloseSecureChannel:
				return CLO;
			case SecureMessage:
				return MSG;
			default:
				throw new UaException(StatusCodes.Bad_TcpMessageTypeInvalid, "unknown message type: " + messageType);
		}
	}

	public static MessageType fromMediumInt(int messageType) throws UaException {
		switch (messageType) {
			case HEL:
				return Hello;
			case ACK:
				return Acknowledge;
			case ERR:
				return Error;
			case OPN:
				return OpenSecureChannel;
			case CLO:
				return CloseSecureChannel;
			case MSG:
				return SecureMessage;
			default:
				throw new UaException(StatusCodes.Bad_TcpMessageTypeInvalid, "unknown message type: " + messageType);
		}
	}

}
