package org.documentoviscode.splashyapi.controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@RestController
public class GoogleDriveController {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    private static final String USER_IDENTIFIER_KEY = "USER";

    @Value("${google.oauth.callback.uri}")
    private String CALLBACK_URI;

    @Value("${google.secret.key.path}")
    private Resource gdSecretKeys;

    @Value("${google.credentials.folder.path}")
    private Resource credentialsFolder;

    private GoogleAuthorizationCodeFlow flow;

    /**
     * Initializes the Google Drive controller.
     * @throws Exception if there is an error during initialization.
     */
    @PostConstruct
    public void init() throws Exception {
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(gdSecretKeys.getInputStream()));
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(credentialsFolder.getFile())).build();
    }

    /**
     * Initiates Google OAuth sign-in and redirects the user to Google's authentication page.
     * @param response The HttpServletResponse to redirect the user.
     * @throws Exception if there is an error during the sign-in process.
     */
    @GetMapping(value = { "/signin" })
    public void doGoogleSignIn(HttpServletResponse response) throws Exception {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        String redirectURL = url.setRedirectUri(CALLBACK_URI).setAccessType("offline").build();
        response.sendRedirect(redirectURL);
    }

    /**
     * Handles the callback from Google OAuth and saves the authorization code.
     * @param request The HttpServletRequest containing the authorization code.
     * @throws Exception if there is an error during the code saving process.
     */
    @GetMapping(value = { "/oauth" })
    public void saveAuthorizationCode(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        if (code != null) {
            saveToken(code);
        }
    }

    /**
     * Saves the authorization code and exchanges it for an access token.
     * @param code The authorization code to be exchanged.
     * @throws Exception if there is an error during the token exchange process.
     */
    private void saveToken(String code) throws Exception {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
        flow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);
    }

    /**
     * Downloads a file from Google Drive based on the provided fileId.
     *
     * @param fileId The ID of the file to be downloaded from Google Drive.
     * @return The file content along with appropriate HTTP headers for download.
     * @throws IOException If an error occurs while downloading the file from Google Drive.
     */
    @GetMapping(value = { "/download/{fileId}" })
    public ResponseEntity<byte[]> downloadFile(@PathVariable(name = "fileId") String fileId) throws IOException {
        Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

        try {
            File file = drive.files().get(fileId).execute();

            String fileName = file.getName();
            String mimeType = file.getMimeType();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);

            byte[] fileContent = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to download file: " + e.getDetails());
            throw e;
        }
    }

    /**
     * Lists files in Google Drive.
     *
     * @return A list of files from Google Drive.
     * @throws Exception If an error occurs while retrieving the list of files.
     */
    @GetMapping(value = { "/listfiles" })
    public FileList listFiles() throws Exception {
        Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred).build();

        try {
            FileList fileList = drive.files().list().execute();
            return fileList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to list files: " + e.getDetails());
            throw e;
        }
    }

    /**
     * Creates a file in Google Drive based on the provided MultipartFile.
     *
     * @param file The MultipartFile to be uploaded and created in Google Drive.
     * @return The ID of the created file in Google Drive.
     * @throws Exception If an error occurs while uploading the file to Google Drive.
     */
    @PostMapping(value = { "/create" })
    public String createFile(@RequestParam("file") MultipartFile file) throws Exception {
        Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        InputStreamContent mediaContent = new InputStreamContent(file.getContentType(), file.getInputStream());

        try {
            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + uploadedFile.getId());
            return uploadedFile.getId();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }
}
