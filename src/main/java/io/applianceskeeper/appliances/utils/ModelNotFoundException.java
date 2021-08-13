package io.applianceskeeper.appliances.utils;

import javassist.NotFoundException;

public class ModelNotFoundException extends NotFoundException {

    public ModelNotFoundException() {
        super("Model not found");
    }
}
