package io.applianceskeeper.technicians.utils;

import javassist.NotFoundException;

public class TechnicianNotFoundException extends NotFoundException {
    public TechnicianNotFoundException() {
        super("Technician not found.");
    }
}
