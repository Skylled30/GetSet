package com.example.getset;

public class TakeCardsResponse {
    String status;
    int cards_left;
    int point;

    public TakeCardsResponse(String status, int cards_left, int point) {
        this.status = status;
        this.cards_left = cards_left;
        this.point = point;
    }

    @Override
    public String toString() {
        return "TakeCardsResponse{" +
                "status='" + status + '\'' +
                ", cards_left=" + cards_left +
                ", point=" + point +
                '}';
    }
}
