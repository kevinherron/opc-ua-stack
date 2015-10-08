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

package com.digitalpetri.opcua.stack.core;

public class UaRuntimeException extends RuntimeException {

    private final long statusCode;

    public UaRuntimeException(Throwable cause) {
        super(cause);

        this.statusCode = StatusCodes.Bad_InternalError;
    }

    public UaRuntimeException(long statusCode) {
        this.statusCode = statusCode;
    }

    public UaRuntimeException(long statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    public UaRuntimeException(long statusCode, Throwable cause) {
        super(cause);

        this.statusCode = statusCode;
    }

    public long getStatusCode() {
        return statusCode;
    }

}
