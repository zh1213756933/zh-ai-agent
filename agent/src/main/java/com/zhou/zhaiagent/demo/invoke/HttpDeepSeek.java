package com.zhou.zhaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 使用Hutool调用DeepSeek聊天补全API的示例
 *     /**
 *      * 响应输出示例:
 *      * {
 *      *     "id": "655bfe0e-1001-4827-958d-74271bcac13c",
 *      *     "object": "chat.completion",
 *      *     "created": 1771988373,
 *      *     "model": "deepseek-chat",
 *      *     "choices": [
 *      *         {
 *      *             "index": 0,
 *      *             "message": {
 *      *                 "role": "assistant",
 *      *                 "content": "你好！非常高兴能成为你AI学习之旅中的伙伴！🎉 通过API与我对话确实是探索人工智能的绝佳方式，这标志着你在技术实践上迈出了重要的一步。\n\n无论你是想测试API功能、学习如何集成AI模型，还是想尝试开发智能应用，我都会全力协助你。你可以：\n- 发送代码片段让我帮你分析\n- 询问API使用的最佳实践\n- 探讨AI相关的技术问题\n- 或者就是简单地聊天交流\n\n有什么特别想尝试或了解的吗？我很期待看到你的学习进展！✨\n\n记得随时分享你的想法和遇到的问题，我会一直在这里支持你的学习旅程！🚀"
 *      *             },
 *      *             "finish_reason": "stop"
 *      *         }
 *      *     ],
 *      *     "usage": {
 *      *         "prompt_tokens": 38,
 *      *         "completion_tokens": 135,
 *      *         "total_tokens": 173,
 *      *         "prompt_tokens_details": {
 *      *             "cached_tokens": 0
 *      *         },
 *      *         "prompt_cache_hit_tokens": 0,
 *      *         "prompt_cache_miss_tokens": 38
 *      *     },
 *      *     "system_fingerprint": "fp_eaab8d114b_prod0820_fp8_kvcache"
 *      * }
 *      *
 */
public class HttpDeepSeek {

    public static void main(String[] args) {
        // 从环境变量获取API密钥（推荐使用环境变量避免硬编码）
        String apiKey = ApiKey.DEEP_SEEK_API_KEY;
        if (apiKey.isBlank()) {
            throw new IllegalStateException("请设置环境变量 DEEPSEEK_API_KEY");
        }

        // API端点
        String url = "https://api.deepseek.com/chat/completions";

        // 构建请求体（使用Hutool的JSON工具，代码简洁且类型安全）
        JSONObject requestBody = JSONUtil.createObj()
                // 指定模型版本
                .set("model", "deepseek-chat")
                // 指定系统和用户提示词
                .set("messages", new JSONArray()
                        .put(new JSONObject().set("role", "system").set("content", "You are a helpful assistant."))
                        .put(new JSONObject().set("role", "user").set("content", "你好deepSeek,这是我第一次尝试通过api的方式和你对话沟通," +
                                "也是我ai学习路线关键的一步,感谢你的陪伴!"))
                )
                .set("stream", false);

        // 发送POST请求（使用try-with-resources自动关闭响应）
        try (HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .body(requestBody.toString())       // 直接传入JSON字符串
                .execute()) {

            // 获取响应内容
            String responseBody = response.body();
            System.out.println("API响应：");
            System.out.println(JSONUtil.toJsonPrettyStr(responseBody));


            // 若需要将响应解析为JSON对象进行处理：
            // JSONObject jsonResponse = JSONUtil.parseObj(responseBody);
            // String content = jsonResponse.getJSONArray("choices")
            //        .getJSONObject(0).getJSONObject("message").getStr("content");
            // System.out.println("AI回复：" + content);

        } catch (Exception e) {
            System.err.println("请求失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}