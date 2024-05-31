// GUI-related imports
import java.awt.*;
import java.awt.event.*;

public class ProcessManagement extends Frame implements ActionListener
{
	SchedulingAlgorithms s = new SchedulingAlgorithms();
	
	//Array
	Font font1 = new Font("Calibri",Font.BOLD, 16);
	Font font2 = new Font("Calibri",Font.BOLD, 12);
	 
	String command = "";
		
	public static void main(String[] args)
	{
		Frame frame = new ProcessManagement();
		frame.setResizable(false);
		frame.setSize(1250,800);
		frame.setVisible(true);
		Font f1= new Font("Calibri",Font.BOLD, 12);
		frame.setFont(f1);
		
	}
	
	public ProcessManagement()
	{
		setTitle("Process Management Algorithms");
		
		// Create Menu Bar
		   			
		MenuBar mb = new MenuBar();
		setMenuBar(mb);
		
		// Create Menu Group Labeled "File"
		
		Menu FileMenu = new Menu("File");
		
		// Add it to Menu Bar
		
		mb.add(FileMenu);
		// Create Menu Items
		// Add action Listener 
		// Add to "File" Menu Group
		MenuItem miAbout = new MenuItem("About");
		miAbout.addActionListener(this);
		FileMenu.add(miAbout);
		
		MenuItem miOpen = new MenuItem("Open Data File");
		miOpen.addActionListener(this);
		FileMenu.add(miOpen);
		
		
		MenuItem miExit = new MenuItem("Exit");
		miExit.addActionListener(this);
		FileMenu.add(miExit);

		// Create Menu Group Labeled "File"
		
		Menu AlgMenu = new Menu("Algorithms");
		
		// Add it to Menu Bar
		
		mb.add(AlgMenu);
		
		// Create Menu Items
		// Add action Listener 
		// Add to "Search" Menu Group
		
		MenuItem miFCFS = new MenuItem("First Come First Served");
		miFCFS.addActionListener(this);
		AlgMenu.add(miFCFS);
						
		MenuItem miSJN = new MenuItem("Shortest Job Next");
		miSJN.addActionListener(this);
		AlgMenu.add(miSJN);
		
		MenuItem miRR = new MenuItem("Round Robin");
		miRR.addActionListener(this);
		AlgMenu.add(miRR);	
		
		
		MenuItem miAll = new MenuItem("Run All");
		miAll.addActionListener(this);
		AlgMenu.add(miAll);	
		
		MenuItem miBonus = new MenuItem("Bonus");
		miBonus.addActionListener(this);
		AlgMenu.add(miBonus);	
		
        
		WindowListener l = new WindowAdapter()
		{
						
			public void windowClosing(WindowEvent ev)
			{
				System.exit(0);
			}
			
			public void windowActivated(WindowEvent ev)
			{
				repaint();
			}
			
			public void windowStateChanged(WindowEvent ev)
			{
				repaint();
			}
		
		};
		
		ComponentListener k = new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e) 
			{
        		repaint();           
    		}
		};
		
		// register listeners
			
		this.addWindowListener(l);
		this.addComponentListener(k);

	}
	
//******************************************************************************
//  called by windows manager whenever the application window performs an action
//  (select a menu item, close, resize, ....
//******************************************************************************

	public void actionPerformed (ActionEvent ev)
		{
			// figure out which command was issued
			
			command = ev.getActionCommand();
			
			// take action accordingly
	switch(command)	
	{
	case "Open Data File":
		{
			s = new SchedulingAlgorithms();	
			s.openDataFile();	
			repaint();
			break;
		}
	case "About":
		{
		repaint();
		break;
		}	
	case "Exit":
		{
			System.exit(0);
		}
	case "First Come First Served":
	{		
		s.FCFS();
		repaint();
		break;
	}
	case "Shortest Job Next":
	{
		s.SJN();
		repaint();
		break;
	}
	case "Round Robin":
	{
		s.roundRobin();			
		repaint();
		break;
	}
			
	case "Run All":
	{
		s.FCFS();
		s.SJN();
		s.roundRobin();
		
		repaint();
		break;
	}
	case "Bonus":
	{
		s.roundRobinBonus();
		repaint();
		break;
	}	
}	
}
//********************************************************
// called by repaint() to redraw the screen
//********************************************************
		
		public void paint(Graphics g)
		{
			GraphicsEnvironment e =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
					String[] fontnames = e.getAvailableFontFamilyNames();
			/*		for (int i = 0; i < fontnames.length; i++)
					System.out.println(fontnames[i]);*/
		
		switch(command)
		{
		case "Open Data File":
		{
			g.setFont(font1);
			g.setColor(Color.RED);
			// Acknowledge that file was opened
			if (s.dataFileName != null)
			{
				g.drawString("File --  "+s.dataFileName+"  -- was successfully opened", 400, 200);
				g.drawString("Number of Processes = "+Integer.toString(s.nProcesses), 500, 250);
			}
			else
			{
				g.drawString("NO Data File is Open", 300, 200);
			}
			
			return;	
		}
		case "About":
		{
			// Draw Axis Lines
			Font f = new Font(Font.SANS_SERIF, Font.BOLD,12);
			g.setFont(f);
			int ww = (int)this.getWidth();
			int x = ww/2;
			int y = 100;
			g.drawString(" Process Management",x-40, y);
			g.drawLine (x-350,120, x+350,120);
			g.drawString(" This program is a Java application that simulates three process scheduling algorithms" , x-230, 140);
			g.drawString(" and displays the results of each algorithm:" , x-250, 160);
			x = x-60;
			g.setColor(Color.RED);
			g.drawString(" - First Come First Served (FCFS)", x, 200);	
			g.drawString(" - Shortest Job Next (SJN)", x, 230);
			g.drawString(" - Round Robin (RR)", x, 260);
			g.setColor(Color.BLACK);
			g.drawString(" The application allows the user to load process data from a file, run a single algorithm, " , x-170, 300);
			g.drawString(" run all algorithms, or run a bonus algorithm. The program also calculates and displays the" , x-192, 320);
			g.drawString(" average wait time and average turnaround time for each algorithm. The bonus algorithm " , x-192, 340);
			g.drawString(" displays the results for runing Round Robin using time slices from 50 to 250 in 25 increments." , x-192, 360);
			break;
			
		}
			
		case "First Come First Served":
		{
			DisplayResults(g, "First Come First Served (FCFS)", s.averageWaitTime[0], s.averageTRTime[0]);
			break;
		}
			
		case "Shortest Job Next":
		{
			DisplayResults(g, "Shortest Job Next (SJN)", s.averageWaitTime[1], s.averageTRTime[1]);
			break;	
		}
		case "Round Robin":
		{
			DisplayResults(g, "Round Robin (RR)", s.averageWaitTime[2], s.averageTRTime[2]);
			break;
		}
			
		case "Run All":
			{
				g.setColor(Color.RED);
				g.setFont(font1);
				
				g.drawString("Number Of Processes  = "+Integer.toString(s.nProcesses),500, 100);
				
				g.drawString("TimeQunatum  = 100",500, 140);
				
				g.setColor(Color.BLACK);
				
				
				g.drawString("Scheduling Policy", 250, 200);
				g.drawString("Average Wait Time ", 520, 200);
				g.drawString("Average TurnAround Time", 700, 200);
				g.drawLine(200, 220, 900, 220);
				
				g.drawString("FCFS", 250, 250);
				g.drawString(Double.toString(s.averageWaitTime[0]), 550, 250);
				g.drawString(Double.toString(s.averageTRTime[0]), 750, 250);
				
				g.drawString("SJN", 250, 270);
				g.drawString(Double.toString(s.averageWaitTime[1]), 550, 270);
				g.drawString(Double.toString(s.averageTRTime[1]), 750, 270);
				
				g.drawString("Round Robin", 250, 290);
				g.drawString(Double.toString(s.averageWaitTime[2]), 550, 290);
				g.drawString(Double.toString(s.averageTRTime[2]), 750, 290);
				
				break;
			}
		case "Bonus":
			{
				g.setColor(Color.RED);
				g.setFont(font1);
				
				g.drawString("Number Of Processes  = "+Integer.toString(s.nProcesses),500, 150);
				
				g.setColor(Color.BLACK);
				
				
				g.drawString("Time Slice", 350, 200);
				g.drawString("Average Wait Time ", 520, 200);
				g.drawString("Average TurnAround Time", 700, 200);
				g.drawLine(300, 220, 900, 220);
				
				int y = 250;
				for (int i=0; i<s.timeSlice.length; i++)
				{
					g.drawString(Integer.toString(s.timeSlice[i]), 380, y);
					g.drawString(Double.toString(s.bonusWaitTime[i]), 550, y);
					g.drawString(Double.toString(s.bonusTurnaroundTime[i]), 750, y);
					y = y + 25;
				}
			break;
			}
	}
		
}


		public void DisplayResults(Graphics g, String title, double avgWait, double avgTrnd)
		{
			Font font1= new Font("Calibri", Font.BOLD, 16);
			g.setFont(font1);
			g.setColor(Color.red); 
			g.drawString("Number Of Processes ",500, 95);
			g.drawString(" : "+s.nProcesses,655, 95);
			g.drawString("Total Period of Time ",500, 110);
			g.drawString(" : "+s.time,655, 110);
			g.drawString("Time Quantum   ", 500, 125);
			g.drawString(" : 100 ", 655, 125);

			g.drawString("Scheduling Policy: "+title,450, 160);
			Font font2 = new Font ("Calibri", Font.BOLD, 12);
			g.setFont(font2);
			g.setColor(Color.BLACK);
			if(s.nProcesses >30)
				{
				int x = 50;
				int y = 200;
				for (int i=0; i<3; i++)
				{
					g.drawString("ID", x, y);
					g.drawString("Priority", x+35, y);
					g.drawString("RunTime", x+90, y);
					g.drawString("Arrival", x+150, y);
					g.drawString("Wait ", x+225, y);
					g.drawString("TurnAround", x+280, y);
					y=y+15;
					g.drawLine(x, y, x+350, y);
					
					for (int j=0; j<30; j++)
					{
						y=y+15;
						g.drawString(Integer.toString(i*30+j), x, y);
						g.drawString(Integer.toString(s.priority[i*30+j]), x+40, y);
						g.drawString(Integer.toString(s.processTime[i*30+j]), x+100, y);
						g.drawString(Integer.toString(s.arrivalTime[i*30+j]), x+160, y);
						g.drawString(Integer.toString(s.waitTime[i*30+j]), x+230, y);
						g.drawString(Integer.toString(s.turnaroundTime[i*30+j]), x+300, y);
					}
					x=x+400;
					y=200;
				}
				g.setFont(font1);
				g.setColor(Color.RED);
				g.drawString("Average Wait Time ", 470, 730);
				g.drawString("Average TurnAround Time", 700, 730);
				g.drawLine(400, 745, 950, 745);
				g.setColor(Color.BLACK);
				g.drawString(Double.toString(avgWait), 510, 780);
				g.drawString(Double.toString(avgTrnd), 750, 780);	
			}
			else
			{
				int x = this.getWidth()/3+20;
				int y = 200;
				
				g.drawString("ID", x, y);
				g.drawString("Priority", x+35, y);
				g.drawString("RunTime", x+90, y);
				g.drawString("Arrival", x+150, y);
				g.drawString("Wait ", x+225, y);
				g.drawString("TurnAround", x+280, y);
				y=y+15;
				g.drawLine(x-10, y, x+340, y);
					
				for (int j=0; j<s.nProcesses; j++)
				{
					y = y + 15;
					g.drawString(Integer.toString(j), x, y);
					g.drawString(Integer.toString(s.priority[j]), x+40, y);
					g.drawString(Integer.toString(s.processTime[j]), x+100, y);
					g.drawString(Integer.toString(s.arrivalTime[j]), x+160, y);
					g.drawString(Integer.toString(s.waitTime[j]), x+230, y);
					g.drawString(Integer.toString(s.turnaroundTime[j]), x+300, y);
					
				}
				
				y=y+30;
				
				g.setFont(font1);
				g.setColor(Color.RED);
				g.drawString("Average Wait Time ", x, y);
				g.drawString("Average TurnAround Time", x+170, y);
				y = y +20;
				g.drawLine(x-10, y, x+340, y);
				g.setColor(Color.BLACK);
				y = y +20;
				g.drawString(Double.toString(avgWait), x+50, y);
				g.drawString(Double.toString(avgTrnd), x+250, y);	
			}
		}

}