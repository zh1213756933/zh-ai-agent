package com.zhou.zhaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * springAi调用ds LLM
 */
@Component
public class SpringAiDeepSeek implements CommandLineRunner {

    @Resource
    private ChatModel dashScopeChatModel;

    @Override
    public void run(String... args) throws Exception {
//        AssistantMessage output = Objects.requireNonNull(
//                dashScopeChatModel.call(new Prompt("你好,这是我第一次尝试通过SpringAi框架以代码实现的方式和你对话沟通,我想向你咨询一些有趣的事情." +
//                                "想请你告诉我射手座的人格分析是什么样子的并告诉我你是什么语言模型?"))
//                .getResult()
//        ).getOutput();
//        System.out.println(output.getText());
    }
}
