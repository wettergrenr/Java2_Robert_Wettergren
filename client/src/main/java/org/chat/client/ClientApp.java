package org.chat.client;

import org.chat.api.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.chat.api.Server;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        String inputLine = null;
        Server stub = (Server) registry.lookup("ChatAppService");

        Client ts = new ClientImpl();
        Client clientProxy = (Client) UnicastRemoteObject.exportObject(ts, 0);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        try {
            inputLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stub.register(clientProxy, inputLine);

        inputLine = null;
        while (!"QUIT".equals(inputLine)) {
            try {
                inputLine = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            stub.submit(clientProxy, inputLine);
        }
        System.out.println("Deregestering client");
        stub.deRegister(clientProxy);
        UnicastRemoteObject.unexportObject(ts, true);
    }
}
