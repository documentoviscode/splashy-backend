package org.documentoviscode.splashyapi.services;

import io.camunda.tasklist.CamundaTaskListClient;
import io.camunda.tasklist.auth.SaasAuthentication;
import io.camunda.tasklist.dto.TaskList;
import io.camunda.tasklist.dto.TaskSearch;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Service
public class DocumentCirculationService {

    private static final String zeebeAPI = "de57c218-8a31-4edc-ac03-e7d8c798e3a0.bru-2.zeebe.camunda.io:443";
    private static final String taskListAPI = "https://bru-2.tasklist.camunda.io/de57c218-8a31-4edc-ac03-e7d8c798e3a0";
    private static final String audience = "zeebe.camunda.io";
    private static final String clientId = "u51e.uTxLdtuQelHVdbSdV.hl1o__-Np";
    private static final String clientSecret = "RwsxuRWXyNGC-RUIwEQxczE0oBeutkrjy~wRTxIJgaR~PXPaOOxcf3c8yhVel5W6";
    private static final String oAuthAPI = "https://login.cloud.camunda.io/oauth/token";

    private static final String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlFVVXdPVFpDUTBVM01qZEVRME0wTkRFelJrUkJORFk0T0RZeE1FRTBSa1pFUlVWRVF6bERNZyJ9.eyJodHRwczovL2NhbXVuZGEuY29tL29yZ0lkIjoiYTJiYWIyMzAtMzE1NC00OGY4LWIzYjctMzc4MGFmN2UxOTg2IiwiaXNzIjoiaHR0cHM6Ly93ZWJsb2dpbi5jbG91ZC5jYW11bmRhLmlvLyIsInN1YiI6Im13bzkwdDJyMzE2MDd6MzZCTkg2OXRXRktCWDU1ajFXQGNsaWVudHMiLCJhdWQiOiJ6ZWViZS5jYW11bmRhLmlvIiwiaWF0IjoxNjk5NzgxMjcxLCJleHAiOjE2OTk4Njc2NzEsImF6cCI6Im13bzkwdDJyMzE2MDd6MzZCTkg2OXRXRktCWDU1ajFXIiwic2NvcGUiOiJkZTU3YzIxOC04YTMxLTRlZGMtYWMwMy1lN2Q4Yzc5OGUzYTAiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.LbpiAbKuAWGplzBGN5XmGZGqqchWOMmPoD1W2CVjOQwtRQ6wDiBXWSmMRqJiRzMFBYKeiWbHsFu4e6Xn0cYqiO5W4TcyCRqlreGRr638XEnfyqjxoQFZpyd0MOrSNYtB7UjyvLPWNVOd-F2TD6t9gtno25THrKQvGlSkpPcY3iajZF7epTohVGxaeEkbKMaFhASZTGZQDE89a6RukjExEYmQfARAg-FgEQ_mSjAyvSmXIVWrP4OENdtrqC-3FwM-e96I-BU56heimJuWQZgwks3YDcYBBnRdr4ZVof5S6gaU2pAcvO923V-CFaEeQKjvBPFKEMxsllpa333t2UcxtA";

    private final ZeebeClient client;
    private final DeploymentEvent deploymentEvent;
    private final CamundaTaskListClient taskListClient;

    private ProcessInstanceEvent processInstanceEvent;

    public DocumentCirculationService() {
        OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(oAuthAPI)
                        .audience(audience)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .build();

        client = ZeebeClient.newClientBuilder()
                .gatewayAddress(zeebeAPI)
                .credentialsProvider(credentialsProvider)
                .build();

        client.newTopologyRequest().send().join();

         deploymentEvent =
                client.newDeployResourceCommand()
                        .addResourceFromClasspath("contract-extension.bpmn")
                        .send()
                        .join();

        SaasAuthentication sa = new SaasAuthentication(clientId, clientSecret);
        try {
            taskListClient = new CamundaTaskListClient.Builder().authentication(sa)
                    .taskListUrl(taskListAPI).build();
        } catch (TaskListException e) {
            throw new RuntimeException(e);
        }
    }

    public void createProcessInstance() {
        processInstanceEvent = client
                .newCreateInstanceCommand()
                .bpmnProcessId(deploymentEvent.getProcesses().get(0).getBpmnProcessId())
                .latestVersion()
                .send()
                .join();
    }

    public void completeActivityReviewTask() throws TaskListException {

        TaskSearch ts = new TaskSearch()
                .setGroup("documentoviscoAdmin")
                .setTaskDefinitionId("Activity_Review");

        TaskList tasks = taskListClient.getTasks(ts);

        taskListClient.completeTask(tasks.get(0).getId(), Map.of("contractReview", "approved"));
    }
}
