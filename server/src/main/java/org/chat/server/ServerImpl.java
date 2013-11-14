package org.chat.server;

import org.chat.api.MessageObject;
import org.chat.api.Server;
import org.chat.api.Client;
import org.chat.api.database.MessageStore;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ServerImpl implements Server {
    
    private static final Logger log = Logger.getLogger(ServerImpl.class.getName());
    
    HashMap<Client, String> clients = new HashMap<>();

    MessageStore db = new MessageStore();

    @Override
    public void register(Client client, String name) throws RemoteException {

        clients.put(client, name);
        MessageObject message = new MessageObject("ChatApp Server", name + " has joined the chat.");
        sendMessageToAllClients(message);
        log.info("New client registered with " + name);
        sendMessageHistory(client);
    }

    @Override
    public void deRegister(Client client) throws RemoteException {

        String name = clients.get(client);
        clients.remove(client);
        MessageObject message = new MessageObject("Client ", name + " deregistered");
        sendMessageToAllClients(message);
    }

    @Override
    public void submit(Client client, String messageText) throws RemoteException {

        log.info("Received: " + messageText);
        MessageObject message = new MessageObject(clients.get(client), messageText);
        db.storeMessage(message);
        sendMessageToAllClients(message);
    }

    @Override
    public void submit(Client client, String recipient, String messageText) throws RemoteException {

        log.info("Received exclusive message: " + messageText);
        MessageObject message = new MessageObject(clients.get(client), messageText);
        sendMessageToNamedClients(recipient, message);
    }

    @Override
    public void sendMessageToAllClients(MessageObject message) throws RemoteException {

        for (Client client : clients.keySet()) {
            client.broadcast(message);
            log.info("Sending message to all clients: " + message.toString());
        }
    }

    @Override
    public void sendMessageToNamedClients(String recipient, MessageObject message) throws RemoteException {
        for (Client client : clients.keySet()) {
            if (clients.get(client).equalsIgnoreCase(recipient)) {
                client.broadcast(message);
                log.info("Sending exclusive message to named clients: " + message.toString());
            }
        }
    }

    @Override
    public void sendMessageToClient(Client client, MessageObject message) throws RemoteException {
        log.info("Sending exclusive message: " + message.toString());
        client.broadcast(message);
    }

    @Override
    public void sendMessageHistory(Client client) throws RemoteException {
        log.info("Retrieving message history from database");
        List<List<String>> messages = db.getMessageHistory();
        StringBuilder sb = new StringBuilder();
        MessageObject mo = null;

        log.info("Sending message history");
        for (List<String> message : messages) {
            mo = new MessageObject(message.get(0), message.get(1), message.get(2));
            sendMessageToClient(client, mo);
        }
    }
}
