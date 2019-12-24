package com.example.getset;

class Cards {
    int count;
    int color;
    int shape;
    int fill;

    public Cards(int count, int color, int shape, int fill) {
        this.count = count;
        this.color = color;
        this.shape = shape;
        this.fill = fill;
    }

    @Override
    public String toString() {
        return "count " + count + " color " + color + " shape " + shape + " fill " + fill;
    }
}
