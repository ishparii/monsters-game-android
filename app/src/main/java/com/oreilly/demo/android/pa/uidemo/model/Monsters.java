package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.Observable;
import java.util.Random;

import com.oreilly.demo.android.pa.uidemo.view.MonsterGrid;




/** A list of monsters. */
public class Monsters implements Observer {
    /** MonsterChangeListener. */
    public interface MonstersChangeListener {
        /** @param monsters the monsters that changed. */
        void onMonstersChange(Monsters monsters);
    }

    private final LinkedList<Monster> monsters = new LinkedList<>();

    private MonstersChangeListener monstersChangeListener;
    public int column;
    public int row;
    private int totalNumberOfMonsters;
    private int vulnerableProb;


    public Monster[][] positions;


    public MonsterGrid monsterGrid;
    public int killed=0;
    public static  Random ra=new Random();

    public Monsters(int totalMonsterNumberProb, int vulnerableProb){
        this.totalNumberOfMonsters = totalMonsterNumberProb;
        this.vulnerableProb = vulnerableProb;
    }

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
        return monsters;
    }

    public int getTotalNumberOfMonsters() {
        return totalNumberOfMonsters;
    }

    public void setTotalNumberOfMonsters(int totalNumberOfMonsters) {
        this.totalNumberOfMonsters = totalNumberOfMonsters;
    }

    public int getVulnerableProb() {
        return vulnerableProb;
    }

    public void setVulnerableProb(int vulnerableProb) {
        this.vulnerableProb = vulnerableProb;
    }

    /**
     * @param newMonster
      */
    public Monster addMonster(Monster newMonster) {
        newMonster.addObserver(monsterGrid);
        monsters.add(newMonster);
        positions[newMonster.getX()][newMonster.getY()]=newMonster;
        return newMonster;
    }

    public void stopMoving(){
        for(Monster monster:monsters){
            monster.async.cancel(false);
            monster.async = null;
        }
    }

    public void startMoving(){

        for(Monster monster:monsters){
            Object[] params=new Object[2];
            params[0]=positions;
            params[1]=monster;
            monster.async = new Monster.Async();
            monster.async.execute(params);
        }
    }

    public  boolean removeMonster(Monster monster){
        positions[monster.getX()][monster.getY()] = null;
        killed++;
        return monsters.remove(monster);
    }

    /** Remove all monster. */
    public void clearMonsters() {
        monsters.clear();
        killed = 0;
        notifyListener();
    }

    private void notifyListener() {
        if (null != monstersChangeListener) {
            monstersChangeListener.onMonstersChange(this);
        }
    }
    
    @Override
    public synchronized void  update(Observable o, Object arg){}

    public void initializeMonsters(){

        positions = new Monster[column][row];
        for(int i = 0 ; i < column ; i++)
            for(int j = 0 ; j < row ; j++)
                positions[i][j] = null;

        //Random rand = new Random();
        for(int i = 0 ; i < totalNumberOfMonsters ; i++){
            boolean exist = true;
            while (exist){
                int x = ra.nextInt(column);
                int y = ra.nextInt(row);
                if (positions[x][y]==null){
                    exist = false;

                    Monster newMonster=new Monster(x,y,vulnerableProb);
                    addMonster(newMonster);
                }
            }
        }

    }
}
