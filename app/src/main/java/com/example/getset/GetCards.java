package com.example.getset;

import java.util.ArrayList;

class GetCards {
    private String status;
    private ArrayList<Cards> cards;

    @Override
    public String toString() {
        return "GetCards{" +
                "status='" + status + '\'' +
                ", cards=" + cards +
                '}';
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }
}
