package com.appgranula.retrofitbindingconverters.typeadapters;

import com.appgranula.bindingutils.binding.ObservableString;
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
public class ObservableStringTypeAdapter extends TypeAdapter<ObservableString> {
    @Override
    public void write(JsonWriter out, ObservableString value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.value(value.get());
    }

    @Override
    public ObservableString read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        String str = in.nextString();
        return new ObservableString(str);
    }
}
