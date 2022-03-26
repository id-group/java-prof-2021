package ru.idgroup.otus.patterns.handler;


import ru.idgroup.otus.patterns.listener.Listener;
import ru.idgroup.otus.patterns.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
