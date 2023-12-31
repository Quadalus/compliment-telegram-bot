package ru.bikkul.compliment.telegram.bot.util.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserTextCommands {
    private static final Map<Long, Deque<String>> userTextCommand = new ConcurrentHashMap<>();

    private UserTextCommands() {
    }

    public static void addCommand(long chatId, String command) {
        userTextCommand.computeIfAbsent(chatId, commands -> new ArrayDeque<>());
        userTextCommand.get(chatId)
                .addFirst(command);
    }

    public static String peekCoomand(long chatId) {
        return userTextCommand.getOrDefault(chatId, new ArrayDeque<>())
                .peekFirst();
    }

    public static void removeCommand(long chatId) {
        userTextCommand.getOrDefault(chatId, new ArrayDeque<>())
                .pollFirst();
    }
}
