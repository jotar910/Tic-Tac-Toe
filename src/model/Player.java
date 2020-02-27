package model;

public class Player {
    private static int countId;
    private int id;
    private String name;

    public Player(String name) {
        this.name = name;
        id = countId++;
    }

    public Player() {
        id = countId++;
        name = "Player-" + id;
    }

    public static int getCountId() {
        return countId;
    }

    public static void setCountId(int countId) {
        Player.countId = countId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null || !(ob instanceof Player))
            return false;

        return id == ((Player) ob).getId();
    }

    @Override
    public int hashCode() {
        return id;
    }
}