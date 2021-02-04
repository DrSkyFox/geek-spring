package ru.chatclient.history;

import ru.chatclient.TextMessage;

import java.util.List;

public interface ChatHistory {

    void addMessage(TextMessage message);

    List<TextMessage> getLastMessages(int count);

    void flush();
}
