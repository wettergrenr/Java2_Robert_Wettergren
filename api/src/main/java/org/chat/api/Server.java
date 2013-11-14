package org.chat.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    void register(Client client, String name) throws RemoteException;

    void deRegister(Client client) throws RemoteException;

    void submit(Client client, String message) throws RemoteException;

    void submit(Client client, String recipient, String message) throws RemoteException;

    void sendMessageToAllClients(MessageObject message) throws RemoteException;

    void sendMessageToClient(Client client, MessageObject message) throws RemoteException;

    void sendMessageToNamedClients(String recipient, MessageObject message) throws RemoteException;
    
    void sendMessageHistory(Client client) throws RemoteException;
}
