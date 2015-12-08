package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.Observable;
import java.util.Random;

import com.oreilly.demo.android.pa.uidemo.model.Clock.OnTickListener;
import com.oreilly.demo.android.pa.uidemo.view.MonsterGrid;

import static com.oreilly.demo.android.pa.uidemo.model.Monster.*;


/** A list of monsters. */
public class Monsters implements Observer , OnTickListener{
    /** MonsterChangeListener. */
    public interface MonstersChangeListener {
        /** @param monsters the monsters that changed. */
        void onMonstersChange(Monsters monsters);
    }

    private final LinkedList<Monster> monsters = new LinkedList<>();
    //private final List<Monster> safeMonsters = Collections.unmodifiableList(monsters);
    //private final Monster[][] positions=new Monster[1][1];

    private MonstersChangeListener monstersChangeListener;
    public int column;
    public int row;
    public Monster[][] positions;
    public final int MONSTERS_TOTAL=20;
    public static  Random ra=new Random();
    public MonsterGrid monsterGrid;

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

    /**
     * @param x horizontal coordinate at top left corner
     * @param y vertical coordinate at top left corner
     * @param isVulnerable status of monster.
      */
    public Monster addMonster(Monster newMonster) {
        //Monster newMonster=new Monster(x, y, isVulnerable);
        Object[] params=new Object[2];
        params[0]=positions;
        params[1]=newMonster;
        newMonster.addObserver(monsterGrid);
        newMonster.async.execute(params);


        monsters.add(newMonster);
        positions[newMonster.getX()][newMonster.getY()]=newMonster;

       // notifyListener();
        return newMonster;
    }

    public  boolean removeMonster(Monster monster){
         positions[monster.getX()][monster.getY()] = null;
        return monsters.remove(monster);
    }

    public void removeMonster(int x, int y){

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

    @Override
    public synchronized void  update(Observable o, Object arg){
        //Object[] r=(Object[])arg;
       //Monster newMonster=new Monster((int)r[0],(int)r[1],(int)r[2]==1);
       // positions[((Monster)r[3]).getX()][((Monster)r[3]).getY()]=null;

        //addMonster(newMonster);
        //removeMonster(newMonster );
      //  monsters.remove(r[3]);
       // positions[(int)r[0]][(int)r[1]]=newMonster;
       // Object[] params=new Object[2];
       // params[0]=positions;
        //params[1]=r[3];



    }

    public void onTick(){
       // updateMonsters();
    }

    public void updateMonsters(){
        int x;
        int y;
        boolean v;

       // Random rand = new Random();
        int l=getMonsters().size();

        for(int i=0;i<l;i++){//update all the monsters
           // Object arg= getMonsters().get(i).move(positions);

            /*Object[] r=(Object[])arg;
            Monster newMonster=new Monster((int)r[0],(int)r[1],(int)r[2]==1);
            newMonster.addObserver(this);
            monsters.add(newMonster);
            positions[(int)r[0]][(int)r[1]]=newMonster;*/

             // getMonsters().get(i).moved=false;

            // positions[r[0]][r[1]]= this.monsters.addMonster(r[0],r[1],r[2]==1);
        }

        Iterator<Monster> iterator = getMonsters().iterator();
        while(iterator.hasNext()){
            if(iterator.next().isMoved())
                iterator.remove();
        }


        /*for(int i=0;i<MONSTERS_TOTAL-getMonsters().size();i++){//need to add more monsters
            boolean exist = true;
            while (exist){
                x = rand.nextInt(column);
                y = rand.nextInt(row);
                if(positions[x][y]==null){
                    exist = false;
                    positions[x][y] = addMonster(x, y, rand.nextInt(100)%2==0);
                    //monsters.getLastMonster().draw(canvas,  getContext(), squareWidth, leftMargin, topMargin, paint);
                }
            }
        }*/
    }

    public void initializeMonsters(){
        positions = new Monster[column][row];
        for(int i = 0 ; i < column ; i++)
            for(int j = 0 ; j < row ; j++)
                positions[i][j] = null;

        Random rand = new Random();
        for(int i = 0 ; i < MONSTERS_TOTAL ; i++){
            boolean exist = true;
            while (exist){
                int x = rand.nextInt(column);
                int y = rand.nextInt(row);
                if (positions[x][y]==null){
                    exist = false;
                    Monster newMonster=new Monster(x,y,i%2==0);
                     addMonster(newMonster);
                    //monsters.getLastMonster().draw(canvas,  getContext(), squareWidth, leftMargin, topMargin, paint);
                }
            }
        }
    }
}
