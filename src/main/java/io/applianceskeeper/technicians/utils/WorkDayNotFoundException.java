package io.applianceskeeper.technicians.utils;

import javassist.NotFoundException;

public class WorkDayNotFoundException extends NotFoundException {

    public WorkDayNotFoundException() {
        super("Workday not found.");
    }
}
