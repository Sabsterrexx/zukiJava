package org.Modules.MainCallers;
import org.Modules.SubCallers.ZukiChatCall;

public class ZukiChat {

    private String API_KEY;
    private String API_BACKUP_KEY;
    public String API_ENDPOINT;
    private String API_ENDPOINT_UNFILTERED;
    private String API_ENDPOINT_BACKUP;
    private String systemPrompt;
    private String model;
    private double temperature;
    private ZukiChatCall API_CALLER;
    private ZukiChatCall API_BACKUP_CALLER;

    public ZukiChat(String API_KEY, String API_BACKUP_KEY, String model, String systemPrompt, double temperature) {
        this.API_KEY = API_KEY;
        this.API_BACKUP_KEY = API_BACKUP_KEY;
        this.API_ENDPOINT = "https://zukijourney.xyzbot.net/v1/chat/completions";
        this.API_ENDPOINT_UNFILTERED = "https://zukijourney.xyzbot.net/unf/chat/completions";
        this.API_ENDPOINT_BACKUP = "https://thirdparty.webraft.in/v1/chat/completions";

        this.systemPrompt = systemPrompt;
        

        if (0 <= temperature && temperature <= 1)
            this.temperature = temperature;
        else
            throw new IllegalArgumentException("Temperature must be a value between 0 and 1!");

        this.API_CALLER = new ZukiChatCall(this.API_KEY);
        this.API_BACKUP_CALLER = new ZukiChatCall(this.API_BACKUP_KEY);
    }

    public void setSystemPrompt(String newPrompt) {
        if (newPrompt != null && !newPrompt.isEmpty())
            this.systemPrompt = newPrompt;
        else
            throw new IllegalArgumentException("Prompt must be a non-empty string!");
    }

    public void setTemp(double newTemp) {
        if (0 <= newTemp && newTemp <= 1)
            this.temperature = newTemp;
        else
            throw new IllegalArgumentException("Temperature must be a value between 0 and 1!");
    }

    public void changeBackupEndpoint(String newEndpoint) {
        this.API_ENDPOINT_BACKUP = newEndpoint;
    }

    public String sendMessage(String userName, String userMessage) {
        return this.API_CALLER.CHAT_CALL(userName, userMessage, this.model, this.systemPrompt, this.temperature, this.API_ENDPOINT);
    }

    public String sendUnfilteredMessage(String userName, String userMessage) {
        return this.API_CALLER.CHAT_CALL(userName, userMessage, this.model, this.systemPrompt, this.temperature, this.API_ENDPOINT_UNFILTERED);
    }

    public String sendBackupMessage(String userName, String userMessage) {
        return this.API_BACKUP_CALLER.CHAT_CALL(userName, userMessage, this.model, this.systemPrompt, this.temperature, this.API_ENDPOINT_BACKUP);
    }

    private boolean arrayContains(String[] array, String value) {
        for (String element : array) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
