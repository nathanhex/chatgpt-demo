package org.yun.chatgpt.demo;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String token = System.getenv("CHATGPT_TOKEN");
        if (args != null && args.length > 0) {
            token = args[0];
        }
        if (token == null || token.isEmpty()) {
            System.err.println("please input chatgpt token ");
            System.exit(1);
            return;
        }
        GPT3Demo gpt3Demo = new GPT3Demo(token);
        Scanner sco = new Scanner(System.in);
        System.out.println("your question:");
        String question;
        while (sco.hasNext()) {
            question = sco.next();
            if ("quit".equals(question) || "exit".equals(question)) {
                break;
            }
            String a = gpt3Demo.chat("1", question);
            System.out.println("boot:[" + a + "]");
        }
        sco.close();
    }
}
