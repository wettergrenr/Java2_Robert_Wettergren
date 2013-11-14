package org.chat.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.chat.api.MessageObject;

public interface Client extends Remote {
	void broadcast(MessageObject message) throws RemoteException;
}
