package com.actiknow.famdent.receiver;

/**
 * Created by swarajpal on 19-04-2016.
 */
public interface SmsListener {
    public void messageReceived (String messageText, int message_type);
}
