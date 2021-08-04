package io.applianceskeeper.technicians.utils;

public class TechnicianTermNotFoundException extends Throwable {

    public TechnicianTermNotFoundException() {
        super("Technician term not found");
    }
}
