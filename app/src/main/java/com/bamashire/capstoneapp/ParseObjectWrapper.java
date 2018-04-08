package com.bamashire.capstoneapp;

import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by trevor on 4/8/18.
 */

public class ParseObjectWrapper implements Serializable {
    private ParseObject object;

    public ParseObjectWrapper(ParseObject o) {
        object = o;
    }

    public ParseObject getObject() {
        return object;
    }
}
