package ru.idgroup.otus.patterns.listener.homework;

import ru.idgroup.otus.patterns.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
