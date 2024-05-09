package com.example.grupparbete_backend_1.models;

public class BlacklistCheckResponse {

        private String statusText;
        private boolean ok;

        public BlacklistCheckResponse(String statusText, boolean ok) {
            this.statusText = statusText;
            this.ok = ok;
        }
    public BlacklistCheckResponse() {
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
