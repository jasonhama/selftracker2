package edu.uw.jjhama.selftracker;
/*
 * A simple container for Movie info
 */

import java.util.Date;

public class Caffine {
    public String title;
    public int amount;
    public Date time;

    public Caffine(String title, int amount, Date time){
        this.title = title;
        this.amount = amount;
        this.time = time;
    }

    //default constructor; empty Caffine
    public Caffine(){}

    public String toString(){
        return this.title;
    }
}