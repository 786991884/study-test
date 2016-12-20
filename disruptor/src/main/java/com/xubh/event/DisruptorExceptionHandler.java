package com.xubh.event;

import com.lmax.disruptor.ExceptionHandler;
import com.xubh.Performer;
import com.xubh.utils.ThrowableParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class DisruptorExceptionHandler implements ExceptionHandler<ValueEvent> {
    private Logger logger = LoggerFactory.getLogger(DisruptorExceptionHandler.class);
    private Performer performer = null;

    public DisruptorExceptionHandler(Performer performer) {
        this.performer = performer;
    }

    public void handleEventException(Throwable throwable, long l, ValueEvent valueEvent) {
        performer.getHasError().compareAndSet(false, true);
        String message = MessageFormat.format("处理{0}数据出错", valueEvent.getObject().toString());
        logger.error("错误：" + ThrowableParser.toString(throwable));
        logger.error(message);

        valueEvent.setHasError(true);
        valueEvent.setMessage(message);
    }

    public void handleOnStartException(Throwable throwable) {
        logger.error("启动disruptor出错");
        logger.error(ThrowableParser.toString(throwable));
    }

    public void handleOnShutdownException(Throwable throwable) {
        logger.error("关闭disruptor出错");
        logger.error(ThrowableParser.toString(throwable));
    }
}
