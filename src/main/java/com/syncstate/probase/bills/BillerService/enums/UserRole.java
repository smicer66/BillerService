package com.syncstate.probase.bills.BillerService.enums;

public enum UserRole {
    SYSTEM("SYSTEM"),
    CUSTOMER("CUSTOMER"),
    ADMINISTRATOR("ADMINISTRATOR");



    public final String value;

    private UserRole(String value) {
        this.value = value;
    }

    public UserRole valueOfLabel(String label) {
        for (UserRole e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }



}
