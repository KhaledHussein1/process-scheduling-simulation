import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;


public class SchedulingAlgorithms 
{
	String dataFilePath ;
	String dataFileName ;
	int nProcesses;
	int nPriorities;
	int[] processTime ;
	int[] priority;
	int[] arrivalTime;
	int[] waitTime;
	int[] turnaroundTime;
	int[] processed;
	// statistics
	double[] averageWaitTime;
	
	double[] averageTRTime;
	int time;
	// Bonus problem data structures
	int[] timeSlice = {50,75,100,125,150,175,200,225,250};
	double[] bonusWaitTime = new double[9];
	double[] bonusTurnaroundTime = new double[9];

	public SchedulingAlgorithms()
	{
		nProcesses = 0;
		nPriorities = 0;
	}
	public void openDataFile()
	{
		dataFilePath = null;
		dataFileName = null;
		
		  JFileChooser chooser = new JFileChooser();
		  chooser.setDialogType(JFileChooser.OPEN_DIALOG );
		  chooser.setDialogTitle("Open Data File");
	      
	      int returnVal = chooser.showOpenDialog(null);
	      if( returnVal == JFileChooser.APPROVE_OPTION) 
	      	{
	          dataFilePath = chooser.getSelectedFile().getPath();
	          dataFileName = chooser.getSelectedFile().getName();
	        }
	      // read data file and copy it to original array
	      try
	      {
	    	  readFileIntoArray(dataFilePath);
	      }
	      catch (IOException ioe)
	      {
	    	  System.exit(0); 
	      }
	}
	public void readFileIntoArray(String filePath ) throws IOException
	{
		if (filePath != null)
	    {
	  	  int index = 0;
	      averageWaitTime = new double[5];
		  averageTRTime   = new double[5];
		  
	  	  Scanner integerTextFile = new Scanner(new File(filePath));
	  	  nProcesses = integerTextFile.nextInt();
	  	  processTime = new int[nProcesses] ;
		  priority = new int[nProcesses];
		  arrivalTime = new int[nProcesses];
		  waitTime = new int[nProcesses];
		  turnaroundTime = new int[nProcesses];
		  processed = new int[nProcesses];
		  
	  	  while (integerTextFile.hasNext())
	  	  {
	  		  int i = integerTextFile.nextInt();
	  		  
	  		  priority[index] = integerTextFile.nextInt();
	  		 // System.out.println(i+"  -- "+Priority[index]);
	  		  if (priority[index] > nPriorities)
	  			  nPriorities = priority[index];
	  		  arrivalTime[index] = integerTextFile.nextInt();
	  		  processTime[index] = integerTextFile.nextInt();
	  		  index++;
	  	  }
				//  end of file detected
	  	  integerTextFile.close();
	    }
	}
	public void FCFS() 
	{
		time = 0;
		double av=0;
		double tr=0;
		for (int i=0; i<nProcesses; i++)
		{
			turnaroundTime[i] = 0;
			waitTime[i] = 0;
			processed[i] = 0;
		}
		for (int i=0; i<nProcesses; i++)
		{
			waitTime[i] = time-arrivalTime[i];
			av += waitTime[i];
			turnaroundTime[i]=time+processTime[i]-arrivalTime[i];
			tr +=turnaroundTime[i];	
			
			time = processTime[i]+time;
		}
		
			averageWaitTime[0] = Math.round(av/nProcesses);
			averageTRTime  [0]=  Math.round(tr/nProcesses);
		return;
	}
	public void SJN() {
	    time = 0;
	    double av = 0;
	    double tr = 0;
	    int remaining = nProcesses;

	    // Initialize waitTime, turnaroundTime, processed arrays
	    for (int i = 0; i < nProcesses; i++) {
	        waitTime[i] = 0;
	        turnaroundTime[i] = 0;
	        processed[i] = 0;
	    }

	    // Process the tasks using SJN scheduling
	    while (remaining > 0) {
	        int nextProcess = findNextShortest(); // Find the process with the shortest execution time
	        processed[nextProcess] = 1; // Mark the process as processed
	        waitTime[nextProcess] = Math.max(0, time - arrivalTime[nextProcess]); // Calculate wait time
	        av += waitTime[nextProcess]; // Update total wait time
	        time += processTime[nextProcess]; // Process the current task
	        turnaroundTime[nextProcess] = Math.max(0, time - arrivalTime[nextProcess]); // Calculate turnaround time
	        tr += turnaroundTime[nextProcess]; // Update total turnaround time
	        remaining--; // Decrement remaining tasks
	    }

	    // Calculate average wait time and average turnaround time
	    averageWaitTime[1] = Math.round(av / nProcesses);
	    averageTRTime[1] = Math.round(tr / nProcesses);
	}
	public int findNextShortest() {
	    int shortest = -1; // Initialize to an invalid index
	    int shortestTime = Integer.MAX_VALUE; // Initialize to a very large number

	    // Find the process that has not been processed and has arrived first
	    for (int i = 0; i < nProcesses; i++) {
	        if (processed[i] == 0 && arrivalTime[i] <= time) {
	            if (processTime[i] < shortestTime || shortest == -1) {
	                shortest = i;
	                shortestTime = processTime[i];
	            }
	        }
	    }

	    return shortest; // Return the index of the process with the shortest execution time
	}

	public void roundRobin() {
	    int timeQuantum = 100;
	    time = 0;
	    double av = 0;
	    double tr = 0;
	    int[] rem = new int[nProcesses];

	    for (int i = 0; i < nProcesses; i++) {
	        waitTime[i] = 0;
	        turnaroundTime[i] = 0;
	        processed[i] = 0;
	        rem[i] = processTime[i];
	    }

	    int addedProcesses = 0;
	    int currentIndex = 0;
	    int nRemainingProcesses = nProcesses;

	    while (nRemainingProcesses > 0) {
	        if (processed[currentIndex] == 0 && arrivalTime[currentIndex] <= time) {
	            processed[currentIndex] = 1;
	            rem[currentIndex] = processTime[currentIndex];
	            waitTime[currentIndex] = time - arrivalTime[currentIndex];
	            av += waitTime[currentIndex];
	            addedProcesses++;
	        }

	        if (processed[currentIndex] == 1) {
	            int timeSpent = Math.min(rem[currentIndex], timeQuantum);
	            time += timeSpent;
	            rem[currentIndex] -= timeSpent;

	            if (rem[currentIndex] == 0) {
	                processed[currentIndex] = 2;
	                turnaroundTime[currentIndex] = time - arrivalTime[currentIndex];
	                tr += turnaroundTime[currentIndex];
	                nRemainingProcesses--;
	            }
	        }

	        currentIndex = (currentIndex + 1) % nProcesses;

	        if (addedProcesses == nProcesses && processed[currentIndex] == 0) {
	            time++;
	        }
	    }

	    averageWaitTime[2] = Math.round(av / nProcesses);
	    averageTRTime[2] = Math.round(tr / nProcesses);
	}
	public void roundRobinBonus() {
		for (int k = 0; k < timeSlice.length; k++) {
			
		    int timeQuantum = timeSlice[k];
		    time = 0;
		    double av = 0;
		    double tr = 0;
		    int[] rem = new int[nProcesses];
	
		    for (int i = 0; i < nProcesses; i++) {
		        waitTime[i] = 0;
		        turnaroundTime[i] = 0;
		        processed[i] = 0;
		        rem[i] = processTime[i];
		    }
	
		    int addedProcesses = 0;
		    int currentIndex = 0;
		    int nRemainingProcesses = nProcesses;
	
		    while (nRemainingProcesses > 0) {
		        if (processed[currentIndex] == 0 && arrivalTime[currentIndex] <= time) {
		            processed[currentIndex] = 1;
		            rem[currentIndex] = processTime[currentIndex];
		            waitTime[currentIndex] = time - arrivalTime[currentIndex];
		            av += waitTime[currentIndex];
		            addedProcesses++;
		        }
	
		        if (processed[currentIndex] == 1) {
		            int timeSpent = Math.min(rem[currentIndex], timeQuantum);
		            time += timeSpent;
		            rem[currentIndex] -= timeSpent;
	
		            if (rem[currentIndex] == 0) {
		                processed[currentIndex] = 2;
		                turnaroundTime[currentIndex] = time - arrivalTime[currentIndex];
		                tr += turnaroundTime[currentIndex];
		                nRemainingProcesses--;
		            }
		        }
	
		        currentIndex = (currentIndex + 1) % nProcesses;
	
		        if (addedProcesses == nProcesses && processed[currentIndex] == 0) {
		            time++;
		        }
		    }
	
		    bonusWaitTime[k] = Math.round(av / nProcesses);
		    bonusTurnaroundTime[k] = Math.round(tr / nProcesses);
	}
	}
}