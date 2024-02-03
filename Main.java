import org.Modules.ZukiCall;


class Main{
    public static void main(String[] args){

        String API_KEY = "zu-ab6395a778f2341bac302c3efabe40e0"; // Set this value to your API key.
        String API_BACKUP_KEY = ""; // Set this value to your backup API key, if you have one (optional).

        // The ZukiCall class handles sending and receiving messages to our LLM with the API.
        ZukiCall zukiAI = new ZukiCall(API_KEY, API_BACKUP_KEY, "gpt-3.5-turbo");

        // By default, the chat model is gpt-3.5 if the second constructor parameter is not defined upon initialization.
        // By default, the system prompt (third constructor parameter) is "You are a helpful assistant",
        // this can be changed later with .zukiChat.setSystemPrompt().

        // To call the unfiltered endpoint, use .zukiChat.sendUnfilteredMessage().
        // If you have a backup API endpoint, first set it as a backup using .zukiChat.changeBackupEndpoint("backup-endpoint"),
        // then call .zukiChat.sendBackupMessage().
        String chatResponse = zukiAI.zukiChat.sendMessage("Sabs", "Hey, how's it going?");
        
        // Response will be printed in the console.
        System.out.println("Chat Response: " + chatResponse);

        // To change the temperature of the model, call .zukiChat.setTemp().
        // The value by default is 0.7.
        zukiAI.zukiChat.setTemp(0.5); // Example: set temperature to 0.5

    }
}