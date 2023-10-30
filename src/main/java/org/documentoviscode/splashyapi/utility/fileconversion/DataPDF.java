package org.documentoviscode.splashyapi.utility.fileconversion;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataPDF {
    private List<String> texts = new ArrayList<>();
    private List<List<List<String>>> tables = new ArrayList<>();
    private List<byte[]> images = new ArrayList<>();

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public List<List<List<String>>> getTables() {
        return tables;
    }

    public void setTables(List<List<List<String>>> tables) {
        this.tables = tables;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
