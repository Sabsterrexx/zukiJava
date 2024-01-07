package org.Modules;

import org.Modules.MainCallers.*;;

public class ZukiCall {

    public String API_KEY;
    public String chatModel;
    public ZukiChat zukiChat;

    public ZukiCall(String API_KEY, String chatModel) {
        this.API_KEY = API_KEY;
        this.chatModel = chatModel;
        this.zukiChat = new ZukiChat(API_KEY, chatModel, "You are a helpful assistant.", 0.7);
    }
}
