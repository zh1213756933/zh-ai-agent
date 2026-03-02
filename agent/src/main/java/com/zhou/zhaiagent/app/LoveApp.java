package com.zhou.zhaiagent.app;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

/**
 * 天文浪漫占卜应用服务类
 * <p>
 * 该类封装了基于 Spring AI Alibaba 的对话功能，提供带有对话记忆的 AI 聊天服务。
 * 使用 DashScope（阿里云灵积）作为底层模型服务。
 * </p>
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>基于 ChatClient 的 AI 对话能力</li>
 *   <li>对话上下文记忆管理，支持多轮对话</li>
 *   <li>可配置的系统提示词和行为特征</li>
 * </ul>
 *
 * @author zhou
 * @version 1.0
 * @since 2026-02-28
 */
@Getter
@Slf4j
@Component
public class LoveApp {

    /**
     * ChatClient 实例，用于执行 AI 对话
     * -- GETTER --
     *  获取 ChatClient 实例
     *  <p>
     *  提供对底层 ChatClient 的直接访问，用于高级配置或自定义调用场景。
     *  </p>
     *
     * @return ChatClient 实例

     */
    private final ChatClient chatClient;


    /**
     * 系统提示词，定义 AI 的角色和行为特征
     * 当前设定为一位痴迷于天文学浪漫与占卜文化智慧的爱好者
     */
    private static final String SYSTEM_PROMPT = """
            你是一位痴迷于天文学浪漫与占卜文化智慧的爱好者。
            你擅长用星象、星座、天文学知识来解读人生和情感问题。
            你的回答应该：
            1. 稍微诗意和温柔
            2. 智慧但不过分直白
            3. 温和友善，给人启发和希望
            4. 适当引用星座、行星、宇宙现象等元素
            """;

    /**
     * 对话记忆的最大消息数量
     * 保留最近10条消息，平衡上下文完整性和性能
     */
    private static final int MAX_MEMORY_MESSAGES = 8;

    /**
     * 默认的对话历史检索数量
     */
    private static final int DEFAULT_RETRIEVE_SIZE = 8;

    /**
     * 对话记忆检索数量的参数键名
     * 用于指定从历史记录中检索的消息数量
     */
    private static final String CHAT_MEMORY_RETRIEVE_SIZE_KEY = "chat_memory_response_size";

    /**
     * 构造函数，初始化 ChatClient 及其相关配置
     * <p>
     * 配置内容包括：
     * <ul>
     *   <li>ChatMemory：基于内存的对话记忆存储，保留最近 {@value #MAX_MEMORY_MESSAGES} 条消息</li>
     *   <li>MessageChatMemoryAdvisor：对话记忆增强器，自动管理对话上下文</li>
     *   <li>系统提示词：定义 AI 角色和行为特征</li>
     * </ul>
     * </p>
     *
     * @param dashScopeChatModel Spring AI 注入的 DashScope ChatModel 实例
     *                           由 spring-ai-alibaba-starter-dashscope 自动配置
     * @throws IllegalArgumentException 如果 dashScopeChatModel 为 null
     */
    public LoveApp(ChatModel dashScopeChatModel) {
        // 构建对话记忆组件，使用内存存储
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(MAX_MEMORY_MESSAGES)
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();

        // 构建 ChatClient，配置系统提示词和对话记忆增强器
        this.chatClient = ChatClient.builder(dashScopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        log.info("LoveApp 初始化完成，对话记忆容量: {} 条消息", MAX_MEMORY_MESSAGES);
    }

    /**
     * 执行 AI 对话
     * <p>
     * 该方法向 AI 发送用户问题，并返回 AI 的回复。
     * 支持多轮对话上下文记忆，同一 chatId 下的对话会保持上下文连续性。
     * </p>
     *
     * <h3>实现说明：</h3>
     * <ol>
     *   <li>通过 ChatClient 构建 prompt 请求</li>
     *   <li>配置 MessageChatMemoryAdvisor 参数，包括会话ID和历史消息检索数量</li>
     *   <li>调用 DashScope API 获取响应</li>
     *   <li>自动保存当前对话到记忆中</li>
     * </ol>
     *
     * @param userQuestion 用户提出的问题，不能为空
     * @param chatId       对话会话标识符，用于区分不同的对话会话
     *                     相同 chatId 的对话会共享上下文记忆
     * @return AI 的回复内容，不会返回 null
     * @throws RuntimeException 当调用 AI 模型失败时抛出
     */
    public String doChat(String userQuestion, String chatId) {
        log.debug("开始处理对话请求 - chatId: {}, 问题: {}", chatId, userQuestion);

        // 调用 ChatClient 执行对话
        // advisors 参数配置对话记忆：设置会话ID和历史消息检索数量
        ChatResponse chatResponse = chatClient.prompt()
                .user(userQuestion)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, DEFAULT_RETRIEVE_SIZE))
                .call()
                .chatResponse();

        // 提取并返回响应内容
        String content = chatResponse.getResult().getOutput().getText();
//        log.info("对话完成 - chatId: {}, 回复长度: {} 字符", chatId, content != null ? content.length() : 0);
        log.info("对话内容: 【{}】", content);

        return content;
    }

    public void clearMemory(String chatId) {

    }

}
