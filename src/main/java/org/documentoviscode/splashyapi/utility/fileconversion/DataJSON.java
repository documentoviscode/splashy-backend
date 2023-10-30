package org.documentoviscode.splashyapi.utility.fileconversion;


import org.json.simple.JSONObject;


public class DataJSON extends Data {
    private JSONObject keys = new JSONObject();

    public JSONObject getKeys() {
        return keys;
    }

    public void setKeys(JSONObject keys) {
        this.keys = keys;
    }
}
