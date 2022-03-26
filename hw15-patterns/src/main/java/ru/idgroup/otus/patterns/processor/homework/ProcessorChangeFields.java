package ru.idgroup.otus.patterns.processor.homework;

import ru.idgroup.otus.patterns.model.Message;
import ru.idgroup.otus.patterns.model.ObjectForMessage;
import ru.idgroup.otus.patterns.processor.Processor;

public class ProcessorChangeFields implements Processor {
    @Override
    public Message process(Message message) {
        Message newMessage = new Message.Builder(message.getId())
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
                .field13(new ObjectForMessage())
                .build();
        return newMessage;
    }
}
