package org.chat.server;

import org.chat.api.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ServerProcess {

    private static final Logger log = Logger.getLogger(ServerProcess.class.getName());

    public static void main(String[] args) throws RemoteException, InterruptedException {
        ServerImpl ts = new ServerImpl();

        Server stub = (Server) UnicastRemoteObject.exportObject(ts, 0);

        Registry registry = LocateRegistry.createRegistry(1099);

        registry.rebind("ChatAppService", stub);

        log.info("### Chat server fully up and running ###");
        
        System.out.println("Chat server ready...");
    }
}
