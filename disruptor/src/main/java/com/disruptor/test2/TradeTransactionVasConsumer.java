package com.disruptor.test2;


import com.lmax.disruptor.EventHandler;

/**
 * @author Lenovo
 * @date 2016-10-19
 * @modify
 * @copyright
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {

    @Override
    public void onEvent(TradeTransaction event, long sequence,
                        boolean endOfBatch) throws Exception {
        //do something....
    }

}
