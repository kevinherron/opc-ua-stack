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

package com.digitalpetri.opcua.stack.core.types.builtin;

import java.util.Date;

import com.google.common.base.MoreObjects;

public final class DateTime {

    public static final DateTime MIN_VALUE = new DateTime(0L);

	/** The delta in 100 nanosecond intervals between Java epoch (January 1, 1970) and UTC epoch (Jan 1, 1601). */
	private static final long EPOCH_DELTA = 116444736000000000L;

	private final long utcTime;

	public DateTime() {
		this(javaToUtc(System.currentTimeMillis()));
	}

	public DateTime(long utcTime) {
		this.utcTime = utcTime;
	}

	public DateTime(Date date) {
		this(javaToUtc(date.getTime()));
	}

	/** @return this time as 100 nanosecond intervals since UTC epoch. */
	public long getUtcTime() {
		return utcTime;
	}

	/** @return this time as milliseconds since Java epoch. */
	public long getJavaTime() {
		return utcToJava(utcTime);
	}

	/** @return this time as a {@link Date}. */
	public Date getJavaDate() {
		return new Date(utcToJava(utcTime));
	}

    public boolean isNull() {
        return utcTime == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTime dateTime = (DateTime) o;

        return utcTime == dateTime.utcTime;
    }

    @Override
    public int hashCode() {
        return (int) (utcTime ^ (utcTime >>> 32));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("utcTime", utcTime)
                .add("javaDate", getJavaDate())
                .toString();
    }

    /** @return a {@link DateTime} initialized to now. */
	public static DateTime now() {
		return new DateTime();
	}

	private static long javaToUtc(long javaTime) {
		return (javaTime * 10000L) + EPOCH_DELTA;
	}

	private static long utcToJava(long utcTime) {
		return (utcTime - EPOCH_DELTA) / 10000L;
	}

}
