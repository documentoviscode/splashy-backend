package org.documentoviscode.splashyapi.utility.fileconversion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


public class FileConversionTests {
    @Test
    void readCSV() {
        Document testDocument = new Document();
        testDocument.readFrom("src/test/resources/test.csv");

        assert ((DataCSV)testDocument.getData()).getRows().get(0).equals(List.of("Id", "Name", "Email"));


        for (int i = 1; i < ((DataCSV)testDocument.getData()).getRows().size(); i++) {
            assert ((DataCSV)testDocument.getData()).getRows().get(i).equals(List.of(
                    Integer.toString(i),
                    "John" + i,
                    "john" + i + "@email.com"));
        }
    }

    @Test
    void readJSON() {
        Document testDocument = new Document();
        testDocument.readFrom("src/test/resources/test.json");

        JSONArray users = new JSONArray();
        users.add(new JSONObject(Map.of(
                "id", (long)1,
                "name", "name1",
                "surname", "surname1",
                "email", "email1"
        )));
        users.add(new JSONObject(Map.of(
                "id", (long)2,
                "name", "name2",
                "surname", "surname2",
                "email", "email2"
        )));
        JSONObject testData = new JSONObject(Map.of(
                "id", (long)0,
                "type", "PDF",
                "date", "2023-11-05",
                "users", users
        ));

        assert isJSONEqual(((DataJSON)(testDocument.getData())).getKeys(), testData);
    }

    private boolean isJSONEqual(JSONObject real, JSONObject expected) {
        for (Object k: real.keySet()) {
            Object v = real.get(k);

            if (v.getClass() == JSONArray.class) {
                int i = 0;
                for (Object o: (JSONArray)v) {
                    if (!isJSONEqual((JSONObject) o, (JSONObject) ((JSONArray)expected.get(k)).get(i++))) return false;
                }
            }
            else if (v.getClass() == JSONObject.class) {
                if (!isJSONEqual((JSONObject)v, (JSONObject)expected.get(k))) return false;
            }
            else if (!v.equals(expected.get(k))) return false;
        }
        return true;
    }
}
