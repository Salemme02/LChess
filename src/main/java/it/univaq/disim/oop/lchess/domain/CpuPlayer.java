package it.univaq.disim.oop.lchess.domain;

public class CpuPlayer implements Player{

    private final String name = "BOT";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
