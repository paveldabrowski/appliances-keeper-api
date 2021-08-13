package io.applianceskeeper.clients;

public class ClientStillReferencedException extends Exception {

    public ClientStillReferencedException() {
        super("Client is referenced to another resource.");
    }
}
