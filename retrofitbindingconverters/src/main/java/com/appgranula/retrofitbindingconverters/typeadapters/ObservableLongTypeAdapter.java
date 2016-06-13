package com.appgranula.retrofitbindingconverters.typeadapters;

import android.databinding.ObservableLong;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 20.01.2016
 */
public class ObservableLongTypeAdapter extends TypeAdapter<ObservableLong> {
    @Override
    public void write(JsonWriter out, ObservableLong value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.value(value.get());
    }

    @Override
    public ObservableLong read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        long val = in.nextLong();
        return new ObservableLong(val);
    }
}
