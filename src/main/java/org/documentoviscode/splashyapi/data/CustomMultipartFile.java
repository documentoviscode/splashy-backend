package org.documentoviscode.splashyapi.data;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;


public class CustomMultipartFile implements MultipartFile {
    private String name;
    private String fileName;
    private String contentType;
    private byte[] bytes;
    private InputStream inputStream;

    public CustomMultipartFile(Path path) throws IOException {
        this.name = path.getName(0).toString();
        this.fileName = path.getFileName().toString();
        this.contentType = Files.probeContentType(path);
        if (this.contentType == null) this.contentType = "*/*";
        this.bytes = Files.readAllBytes(path);
        this.inputStream = new FileInputStream(path.toString());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.fileName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.inputStream;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
    }
}
