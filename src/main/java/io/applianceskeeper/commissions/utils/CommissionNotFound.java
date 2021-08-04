package io.applianceskeeper.commissions.utils;

public class CommissionNotFound extends Throwable {

    public CommissionNotFound() {
        super("Commission not found.");
    }
}
