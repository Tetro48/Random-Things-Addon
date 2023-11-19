package net.random.things.split;

public class Split {
    public String name;
    //public String image;
    public boolean currentlyActive;
    public int timeStarted;
    public int timeFinished;
    public int lastTime;
    public Split(/*String img, */String name){
        this.name = name;
        this.timeStarted = 0;
        this.lastTime = 50;
        this.timeFinished = 0;
        this.lastTime = 0;
        this.currentlyActive = false;
    }
}
