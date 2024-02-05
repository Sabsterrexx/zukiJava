package org.Modules.MainCallers;
import org.Modules.SubCallers.ZukiImageCall;
public class ZukiImage {
    private String API_KEY;
    private ZukiImageCall API_CALLER;
    public String API_ENDPOINT = "https://zukijourney.xyzbot.net/v1/images/generations";

    public ZukiImage(String API_KEY){

        this.API_KEY = API_KEY;
        this.API_CALLER = new ZukiImageCall(API_KEY);

    }

    public String generateImage(String API_KEY, String prompt, int generations, String size, String model){

        return API_CALLER.IMAGE_CALL(prompt, generations, size, model, this.API_ENDPOINT);
    }


}
