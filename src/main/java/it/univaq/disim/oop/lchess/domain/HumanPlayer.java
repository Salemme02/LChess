package it.univaq.disim.oop.lchess.domain;

public class HumanPlayer implements Player{

    private String name;

    public HumanPlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
