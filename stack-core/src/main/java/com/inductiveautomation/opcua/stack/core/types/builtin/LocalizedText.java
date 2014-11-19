package com.inductiveautomation.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;
import java.util.Locale;

import com.google.common.base.Objects;

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
        return Objects.toStringHelper(this)
                .add("text", text)
                .add("locale", locale)
                .toString();
    }
}
