package org.easternafricajesuits.adusum.model;

public class DeleteAccountPayload {
    private String id;

    public DeleteAccountPayload(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
