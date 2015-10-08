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

package com.digitalpetri.opcua.stack.core.channel;

public class ChannelParameters {

	private final int localMaxMessageSize;
	private final int localReceiveBufferSize;
	private final int localSendBufferSize;
	private final int localMaxChunkCount;

	private final int remoteMaxMessageSize;
	private final int remoteReceiveBufferSize;
	private final int remoteSendBufferSize;
	private final int remoteMaxChunkCount;

	public ChannelParameters(int localMaxMessageSize,
							 int localReceiveBufferSize,
							 int localSendBufferSize,
							 int localMaxChunkCount,
							 int remoteMaxMessageSize,
							 int remoteReceiveBufferSize,
							 int remoteSendBufferSize,
							 int remoteMaxChunkCount) {

		this.localMaxMessageSize = localMaxMessageSize;
		this.localReceiveBufferSize = localReceiveBufferSize;
		this.localSendBufferSize = localSendBufferSize;
		this.localMaxChunkCount = localMaxChunkCount;
		this.remoteMaxMessageSize = remoteMaxMessageSize;
		this.remoteReceiveBufferSize = remoteReceiveBufferSize;
		this.remoteSendBufferSize = remoteSendBufferSize;
		this.remoteMaxChunkCount = remoteMaxChunkCount;
	}

	public int getLocalMaxMessageSize() {
		return localMaxMessageSize;
	}

	public int getLocalReceiveBufferSize() {
		return localReceiveBufferSize;
	}

	public int getLocalSendBufferSize() {
		return localSendBufferSize;
	}

	public int getLocalMaxChunkCount() {
		return localMaxChunkCount;
	}

	public int getRemoteMaxMessageSize() {
		return remoteMaxMessageSize;
	}

	public int getRemoteReceiveBufferSize() {
		return remoteReceiveBufferSize;
	}

	public int getRemoteSendBufferSize() {
		return remoteSendBufferSize;
	}

	public int getRemoteMaxChunkCount() {
		return remoteMaxChunkCount;
	}

}
