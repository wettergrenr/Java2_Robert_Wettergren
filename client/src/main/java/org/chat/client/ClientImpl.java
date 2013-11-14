package org.chat.client;

import org.chat.api.Client;
import java.rmi.RemoteException;
import org.chat.api.MessageObject;

public class ClientImpl implements Client {

    @Override
    public void broadcast(MessageObject message) throws RemoteException {
        
        System.out.println(message.toString());
    }
    
    
}
