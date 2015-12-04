package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/** A list of monsters. */
public class Monsters {
    /** MonsterChangeListener. */
    public interface MonstersChangeListener {
        /** @param monsters the monsters that changed. */
        void onMonstersChange(Monsters monsters);
    }

    private final LinkedList<Monster> monsters = new LinkedList<>();
    private final List<Monster> safeMonsters = Collections.unmodifiableList(monsters);

    private MonstersChangeListener monstersChangeListener;

    /** @param l set the change listener. */
    public void setMonstersChangeListener(MonstersChangeListener l) {
        monstersChangeListener = l;
    }

    /** @return the most recently added monster. */
    public Monster getLastMonster() {
        return (monsters.size() <= 0) ? null : monsters.getLast();
    }

    /** @return immutable list of monsters. */
    public List<Monster> getMonsters() {
        return safeMonsters;
    }

    /**
     * @param x horizontal coordinate at top left corner
     * @param y vertical coordinate at top left corner
     * @param isVulnerable status of monster.
      */
    public void addMonster(int x, int y, boolean isVulnerable) {
        monsters.add(new Monster(x, y, isVulnerable));
        notifyListener();
    }

    public void removeMonster(float x, float y){

    }

    /** Remove all dots. */
    public void clearMonsters() {
        monsters.clear();
        notifyListener();
    }

    private void notifyListener() {
        if (null != monstersChangeListener) {
            monstersChangeListener.onMonstersChange(this);
        }
    }
}
