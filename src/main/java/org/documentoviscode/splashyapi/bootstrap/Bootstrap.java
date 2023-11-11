package org.documentoviscode.splashyapi.bootstrap;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class Bootstrap implements CommandLineRunner {

    private class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }

    private static final String zeebeAPI = "de57c218-8a31-4edc-ac03-e7d8c798e3a0.bru-2.zeebe.camunda.io:443";
    private static final String audience = "zeebe.camunda.io";
    private static final String clientId = "u51e.uTxLdtuQelHVdbSdV.hl1o__-Np";
    private static final String clientSecret = "RwsxuRWXyNGC-RUIwEQxczE0oBeutkrjy~wRTxIJgaR~PXPaOOxcf3c8yhVel5W6";
    private static final String oAuthAPI = "https://login.cloud.camunda.io/oauth/token";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running bootstrap...");

        OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(oAuthAPI)
                        .audience(audience)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .build();

        ZeebeClient client = ZeebeClient.newClientBuilder()
                .gatewayAddress(zeebeAPI)
                .credentialsProvider(credentialsProvider)
                .build();

        client.newTopologyRequest().send().join();

        final DeploymentEvent deploymentEvent =
                client.newDeployResourceCommand()
                        .addResourceFromClasspath("contract-extension.bpmn")
                        .send()
                        .join();

        final ProcessInstanceEvent processInstanceEvent = client
                .newCreateInstanceCommand()
                .bpmnProcessId(deploymentEvent.getProcesses().get(0).getBpmnProcessId())
                .latestVersion()
                .send()
                .join();

        URL url = new URL("https://bru-2.tasklist.camunda.io/de57c218-8a31-4edc-ac03-e7d8c798e3a0/v1/tasks/search");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        String payload = "{\"state\":\"CREATED\",\"processInstanceKey\":\"" + processInstanceEvent.getProcessInstanceKey() + "\"}";

        try (DataOutputStream outputStream = new DataOutputStream(con.getOutputStream())) {
            outputStream.write(payload.getBytes());
        }

        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            System.out.println("Response: " + response);
        }
        con.disconnect();



        System.out.println("Bootstrap has finished");
    }
}
