package io.applianceskeeper.clients;

public class NoSuchClientFoundException extends Throwable {

    NoSuchClientFoundException() {
        super("Client not found exception.");
    }

    public NoSuchClientFoundException(String message) {
        super(message);
    }
}
