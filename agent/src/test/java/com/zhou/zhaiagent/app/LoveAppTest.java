package com.zhou.zhaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testLoveApp() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        System.out.println("======================第一轮对话========================");
        System.out.println("提问内容:【你好,我是周航】");
        String answer1 = loveApp.doChat("你好,我是周航", chatId);
        System.out.println("======================第二轮对话========================");


        // 第二轮
        System.out.println("提问内容:【我的星座是白羊，你可以帮我分析我的相关星座内涵吗?】");
        String answer2 = loveApp.doChat("我的星座是白羊，你可以帮我分析我的相关星座内涵吗?", chatId);
        System.out.println("======================第三轮对话========================");



        // 第三轮
        System.out.println("提问内容:【你可以结合星座解读我的人格吗?】");
        String answer3 = loveApp.doChat("你可以结合星座解读我的人格吗", chatId);
        System.out.println("======================第四轮对话========================");


        // 第四轮
        System.out.println("提问内容:【请问我的名字是什么,你可以坚定的说出我的名字吗?】");
        String answer4 = loveApp.doChat("请问我的名字是什么,你可以鉴定的说出我的名字吗", chatId);

    }

}