package ru.idgroup.otus.patterns.listener.homework;


import ru.idgroup.otus.patterns.listener.Listener;
import ru.idgroup.otus.patterns.model.Message;
import ru.idgroup.otus.patterns.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private Map<Long, Message> messages = new HashMap<>();

    @Override
    public void onUpdated(Message message) {
        //throw new UnsupportedOperationException();

        ObjectForMessage field13 = new ObjectForMessage();
        var list = new ArrayList<String>();
        list.addAll(message.getField13().getData());
        field13.setData(list);

         messages.put(message.getId(), new Message.Builder(message.getId())
            .field1(message.getField1())
            .field2(message.getField2())
            .field3(message.getField3())
            .field4(message.getField4())
            .field5(message.getField5())
            .field6(message.getField6())
            .field7(message.getField7())
            .field8(message.getField8())
            .field9(message.getField9())
            .field10(message.getField10())
            .field11(message.getField12())
            .field12(message.getField11())
            .field13(field13)
            .build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if(messages.containsKey(id))
            return Optional.of(messages.get(id));
        else
            return Optional.empty();
    }
}
