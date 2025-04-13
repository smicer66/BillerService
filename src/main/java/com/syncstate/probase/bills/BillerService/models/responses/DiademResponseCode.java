package com.syncstate.probase.bills.BillerService.models.responses;

public enum DiademResponseCode {
    SUCCESS("0"), UNSUCCESSFUL("1");

    public final String value;

    private DiademResponseCode(String value) {
        this.value = value;
    }

    public DiademResponseCode valueOfLabel(String label) {
        for (DiademResponseCode e : values()) {
            if (e.value.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
