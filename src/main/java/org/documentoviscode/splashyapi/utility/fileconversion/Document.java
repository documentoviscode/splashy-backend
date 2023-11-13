package org.documentoviscode.splashyapi.utility.fileconversion;

import org.apache.commons.lang3.NotImplementedException;
import org.documentoviscode.splashyapi.config.DocFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Document {
    private Data data;
    private DocFormat format;

    public static Boolean[][] conversionMatrix = {
        {false, true, false}, // CSV  -> { CSV, JSON, PDF }
        {false, false, false}, // JSON -> { CSV, JSON, PDF }
        {false, false, false}  // PDF  -> { CSV, JSON, PDF }
    };


    public Document() {
        this.data = null;
    }
    public Document(Data data) {
        this.data = data;
    }

    public void readFrom(String filename) {
        if (filename.endsWith(".csv")) {
            DataCSV readData = new DataCSV();
            try (BufferedReader br = new BufferedReader(new FileReader(filename)))
            {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    readData.getRows().add(List.of(values));
                }
                data = readData;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.format = DocFormat.CSV;
        }
        else if (filename.endsWith(".json")) {
            DataJSON readData = new DataJSON();
            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(filename)) {
                JSONObject json = (JSONObject) parser.parse(reader);
                readData.setKeys(json);
                data = readData;
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
            this.format = DocFormat.JSON;
        }
        else throw new NotImplementedException();
    }

    public Document convertTo(DocFormat format) {
        Boolean canCovert;
        try {
            canCovert = Document.conversionMatrix[this.format.ordinal()][format.ordinal()];
        }
        catch (IndexOutOfBoundsException e) {
            canCovert = false;
        }

        if (!canCovert) throw new NotImplementedException();

        if (this.format == DocFormat.CSV) {
            if (format == DocFormat.JSON) {
                DataCSV dataCSV = (DataCSV) this.data;
                DataJSON dataJSON = new DataJSON();

                JSONArray items = new JSONArray();
                for (int i = 1; i < dataCSV.getRows().size(); i++) {
                    JSONObject item = new JSONObject();
                    for (int j = 0; j < dataCSV.getRows().get(0).size(); j++) {
                        item.put(dataCSV.getRows().get(0).get(j), dataCSV.getRows().get(i).get(j));
                    }
                    items.add(item);
                }
                dataJSON.getKeys().put("items", items);

                return new Document(dataJSON);
            }
        }
        return new Document();
    }

    public void saveTo(String filename) throws IOException {
        if (filename.endsWith(".csv")) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            DataCSV lines = (DataCSV) data;

            for (List<String> line: lines.getRows()) {
                String dataLine = String.join(",", line);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        else if (filename.endsWith(".json")) {
            DataJSON json = (DataJSON) data;
            Files.write(Path.of(filename), json.getKeys().toJSONString().getBytes());
        }
        else throw new NotImplementedException();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public DocFormat getFormat() {
        return format;
    }

    public void setFormat(DocFormat format) {
        this.format = format;
    }
}
