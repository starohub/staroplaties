/* SH
 *
 * Modified by Staro Hub @ 2020 [ https://github.com/starohub ]
 *
 * Ref: https://github.com/google/gson/blob/gson-parent-2.8.5/gson/src/main/java/com/google/gson/internal/bind/DateTypeAdapter.java
 *
 * SH
 */

/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.starohub.platies.internal.bind;

import com.starohub.platies.Platies;
import com.starohub.platies.JsonSyntaxException;
import com.starohub.platies.TypeAdapter;
import com.starohub.platies.TypeAdapterFactory;
import com.starohub.platies.internal.JavaVersion;
import com.starohub.platies.internal.PreJava9DateFormatProvider;
import com.starohub.platies.internal.bind.util.ISO8601Utils;
import com.starohub.platies.reflect.TypeToken;
import com.starohub.platies.stream.JsonReader;
import com.starohub.platies.stream.JsonToken;
import com.starohub.platies.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for Date. Although this class appears stateless, it is not.
 * DateFormat captures its time zone and locale when it is created, which gives
 * this class state. DateFormat isn't thread safe either, so this class has
 * to synchronize its read and write methods.
 */
public final class DateTypeAdapter extends TypeAdapter<Date> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
    @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
    @Override public <T> TypeAdapter<T> create(Platies platies, TypeToken<T> typeToken) {
      return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new DateTypeAdapter() : null;
    }
  };

  /**
   * List of 1 or more different date formats used for de-serialization attempts.
   * The first of them (default US format) is used for serialization as well.
   */
  private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();

  public DateTypeAdapter() {
    dateFormats.add(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT));
    }
    if (JavaVersion.isJava9OrLater()) {
      dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(DateFormat.DEFAULT, DateFormat.DEFAULT));
    }
  }

  @Override public Date read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }
    return deserializeToDate(in.nextString());
  }

  private synchronized Date deserializeToDate(String json) {
    for (DateFormat dateFormat : dateFormats) {
      try {
        return dateFormat.parse(json);
      } catch (ParseException ignored) {}
    }
    try {
    	return ISO8601Utils.parse(json, new ParsePosition(0));
    } catch (ParseException e) {
      throw new JsonSyntaxException(json, e);
    }
  }

  @Override public synchronized void write(JsonWriter out, Date value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }
    String dateFormatAsString = dateFormats.get(0).format(value);
    out.value(dateFormatAsString);
  }
  
  
}
