package org.documentoviscode.splashyapi.utility.fileconversion;

import java.util.ArrayList;
import java.util.List;

public class DataCSV extends Data {
    private List<List<String>> rows = new ArrayList<>();

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }
}
