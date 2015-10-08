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

import java.util.Locale;
import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

public final class LocalizedText {

    public static final LocalizedText NULL_VALUE = new LocalizedText(null, null);

	private final String locale;
	private final String text;

	/**
	 * @param locale the locale.
	 * @param text   the text in the specified locale.
	 */
	public LocalizedText(@Nullable String locale, @Nullable String text) {
		this.locale = locale;
		this.text = text;
	}

	@Nullable
	public String getLocale() {
		return locale;
	}

	@Nullable
	public String getText() {
		return text;
	}

	public static LocalizedText english(@Nullable String text) {
		return new LocalizedText(Locale.ENGLISH.getLanguage(), text);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LocalizedText that = (LocalizedText) o;

		return !(locale != null ? !locale.equals(that.locale) : that.locale != null) &&
				!(text != null ? !text.equals(that.text) : that.text != null);
	}

	@Override
	public int hashCode() {
		int result = locale != null ? locale.hashCode() : 0;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		return result;
	}

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("locale", locale)
                .toString();
    }
}
