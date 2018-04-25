/*=========================================================
 Grocery Store Queue Simulation
=========================================================
 Project authors: David M. Hanlon, Rojaleen Chhetry,
 Melvin Gaye, and Montrell Jubilee
 COSC 459 - 101
 Started: 04/15/2018
--------------------------------------------------------*/
package cosc459_group_simulation;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Simulation {


private PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
private Random rand = new Random(); //instantiate new Random object    

private Date date = new Date();
private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - hh:mm:ss a"); 
private ArrayList<String> timeStamp = new ArrayList<String>(); //store timestamps

private int totalCustomers; //# of customers served during simulation

private long startTime = System.nanoTime(); //time of initial build
private long simulationTime = 1; //desired time in minutes
private long firstWaitTime = generateWaitTime(); 
private long serviceCustomerAt;


public Simulation(){
    System.out.println("Instantiated new Simulation @ ["
            + dateFormat.format(date) + "]\n" + insertDivider());
} //Simulation()

public void run(){
    //Main program body
    try {
        Thread.sleep(firstWaitTime); //generate wait time for first customer
        System.out.println("Delay until first customer: " + firstWaitTime);
        newCustomer(totalCustomers);
        serveCustomer();
    } catch (InterruptedException e){/*Catch 'em all*/}

    while((System.nanoTime()-startTime)<=(simulationTime*60000000000L)-firstWaitTime){
        try {
            newCustomer(totalCustomers); //enque customer 
            serveCustomer();
        } catch(Exception e){/*Catch 'em all*/}
    }
    System.out.println("Exit");
    System.exit(0); //stop runtime
} //run()

/*
* @return String
*/ 
@Override 
public String toString(){
    return this.pq.toString();
} //toString()

private void serveCustomer(){
    long elapsedTime = System.nanoTime()-startTime;
    while((elapsedTime)<(serviceCustomerAt)){ 
        elapsedTime += System.nanoTime()/10000000;
    }
    if(pq.size()!=0){
        System.out.println("Dequeued customer @[" + dateFormat.format(new Date())
                + "]");
        pq.poll(); //remove first element of queue
    } else {
        System.out.println("ERROR: Queue is empty!");
    }
} //serveCustomer()


private void newCustomer(int ID){
    long elapsedTime = System.nanoTime()-startTime;
    long waitTime = (long)generateWaitTime()*1000000;
    long generateAt = elapsedTime+waitTime;

    //while((elapsedTime)<(generateAt)){/*Wait*/    
    //    elapsedTime += System.nanoTime()/10000000; //increment elapsed time
    //} 
    
    while((elapsedTime)<(generateAt)){/*Wait*/    
        elapsedTime = System.nanoTime()-startTime; //update elapsed time
    } 
    serviceCustomerAt = 0; //reset service wait time value
    System.out.println("Customer # " + totalCustomers + " added to queue. . .");
    totalCustomers++;
    pq.offer(ID); //insert element into PriorityQueue
    System.out.println("Queue size: " + pq.size()); //output linesize
    assignTimestamp(ID); //call assignArrivalTime() method

    //Calculate time until customer served
    waitTime = (long)generateWaitTime()*1000000;
    elapsedTime = System.nanoTime()-startTime;

    serviceCustomerAt = elapsedTime + waitTime;
    System.out.println("Service delay: " + waitTime/1000000);
} //newCustomer()


private void assignTimestamp(int ID){
    timeStamp.add(ID + ": " + dateFormat.format(new Date()));
    System.out.println(timeStamp.get(totalCustomers-1));
} //assignArrivalTime()


private int generateWaitTime(){
    //Local variables
    int Low = 1000;  //1000ms
    int High = 4000; //4000ms
    return rand.nextInt(High-Low) + Low;
}//generateWaitTime()

/*
* @return String
*/
private static String insertDivider(){
    return ("****");
}//insertDivider()
}
