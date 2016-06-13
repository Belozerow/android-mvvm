package com.appgranula.bindingutils.binding.typeadapters;

import com.appgranula.bindingutils.binding.ObservableBoolean;
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
public class ObservableBooleanTypeAdapter extends TypeAdapter<ObservableBoolean> {
    @Override
    public void write(JsonWriter out, ObservableBoolean value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.value(value.get());
    }

    @Override
    public ObservableBoolean read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        boolean val = in.nextBoolean();
        return new ObservableBoolean(val);
    }
}
