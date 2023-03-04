package org.yun.chatgpt.demo;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GPT3Demo {
    private final OpenAiService openAiService;
    /**
     * 存放一个session的一组ChatMessage，
     */
    private final Map<String, ArrayList<ChatMessage>> sessionMap = new HashMap<>();

    /**
     * 控制对话的次数，当10轮对话20个chatmessage对象后强制重新开始一轮对话，
     */
    private int maxRounds = 20;

    public GPT3Demo(String token) {
        this.openAiService = new OpenAiService(token);
    }

    public String chat(String sessionId, String question) {
        log.debug("ChatGPTBoot.chat sessionId: {};que:{}", sessionId, question);
        ArrayList<ChatMessage> chatMessages = sessionMap.get(sessionId);
        //当对话10轮后强制重新开始对话
        if (chatMessages == null || chatMessages.size() > maxRounds) {
            chatMessages = new ArrayList<>();
            sessionMap.put(sessionId, chatMessages);
        }
        chatMessages.add(new ChatMessage("user", question));
        if (log.isDebugEnabled()) {
            log.debug("ChatGPTBoot.chat sessionId={};chatMessages:{}", sessionId,
                    chatMessages.toArray());
        }
        //参数数目请查看readme
        ChatCompletionRequest completionRequest =
                ChatCompletionRequest.builder()
                        .messages(chatMessages)
                        .model("gpt-3.5-turbo")
                        .temperature(0.7)
                        .maxTokens(2500)
                        .topP(1.0)
                        .frequencyPenalty(0.0)
                        .stop(Arrays.asList("\n", " Human:", " AI:"))
                        .presencePenalty(0.0)
                        .build();


        ArrayList<String> c = new ArrayList<>();
        ArrayList<ChatMessage> c2 = new ArrayList<>();
        openAiService.createChatCompletion(completionRequest).getChoices().forEach(v -> {
            ChatMessage message = v.getMessage();
            c2.add(message);
            c.add(message.getContent());
        });
        chatMessages.addAll(c2);
        return String.format("%s", c.toArray());
    }
}
