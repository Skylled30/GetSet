package com.example.getset;

class RegisterResponse {
    String status;
    int token;

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "status='" + status + '\'' +
                ", token=" + token +
                '}';
    }
}
