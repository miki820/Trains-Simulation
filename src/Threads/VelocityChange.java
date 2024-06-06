package Threads;

import Exceptions.RailroadHazardException;
import Train.Locomotive;

//Class that changes locomotive velocity every second (simulation of acceleration and deceleration)
public class VelocityChange extends Thread {

    private final Locomotive locomotive;

    public VelocityChange(Locomotive locomotive){
        this.locomotive = locomotive;
    }

    //For every new created locomotive its velocity is increased or decreased by 3%
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            double x = Math.random();
            double velocity = locomotive.getVelocity();
            double newVelocity;
            if (x > 0.5) newVelocity = velocity + velocity * 0.03;
            else newVelocity = velocity - velocity * 0.03;
            locomotive.setVelocity(newVelocity);
            try {
                //Checking if locomotive speed is too big and if yes, slowing it down
                locomotive.tooFast();
            } catch (RailroadHazardException e) {
                locomotive.setVelocity(100);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
