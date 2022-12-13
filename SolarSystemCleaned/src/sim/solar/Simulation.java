package sim.solar; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.List; 
import sim.solar.planet.PlanetLoader ;
import sim.solar.planet.PlanetInterface;
import com.opencsv.CSVReader;
import java.io.FileReader; 
import java.util.ArrayList;
class Simulation extends JPanel implements Runnable {
  
      
   //TODO: These five variables should be initialized from values in a config file instead of being hardcoded:  
   private static final int screenSize;         // screen size both x and y
   private static final int frameDelay;          // millisec delay to slow down animation speed 
   private static final int maxSolarCount;        // cannot exceed data rows solarconfig.csv 
   private static final int cyclesPerSolarSystem;   // degrees of rotation allotted to each solar system 
   private static final int pauseDelay;  // millisec delay between solar systems 
      
    
   // static block collect constant values from  simulation.csv config file. 
   static {
      List<Integer> sim=new ArrayList<>();
      try{
         final String fileName = "simsolar.csv";
         CSVReader reader = null;

         reader = new CSVReader(new FileReader(fileName));
         String [] nextLine;  
         nextLine = reader.readNext(); 
         while ((nextLine = reader.readNext()) != null) 
         { 
            for ( String token : nextLine)  
            {
            int i=Integer.parseInt(token);
            sim.add(i);
            }
          }
         reader.close(); 
         }           
      catch (Exception e) {  
         e.printStackTrace();  
      }
        screenSize=sim.get(0);
        frameDelay=sim.get(1);
        maxSolarCount=sim.get(2);
        cyclesPerSolarSystem=sim.get(3);
        pauseDelay=sim.get(4);
         }
         
   //These variables remain initialized here in the code, dont move them to config file :
   private static final Color colorBlack = new Color(0,0,0);
   private static final Color colorGreen = new Color(30,120,30); 
   private int cycleCount = 1;       // must start at one
   private int solarCounter = 0;   // must start at zero
   private static final int screenMid = screenSize/2; // mid-screen location 
   
   private final PlanetLoader planetLoader = new  PlanetLoader();  
   private SolarSystem solarSystem  ;

   public Simulation() {  

       List<PlanetInterface> planetList = planetLoader.Produce(solarCounter); 
       solarSystem = new SolarSystem(planetList); 
       setBackground(colorBlack);
       setPreferredSize( new Dimension(screenSize, screenSize) );
       final Thread t = new Thread (this);
       t.start ();
  }
  
  private void NextSolarSystemRun() {
       try {
         // this will slow down display animation
         Thread.sleep(frameDelay);   
         
         // switch to next solar system
         if ((cycleCount % cyclesPerSolarSystem) == 0) {
             solarCounter++; 
             if (solarCounter > maxSolarCount) {
                solarCounter=0; // rollover to first planetary config
             }
             // get next set of planets to view 
             List<PlanetInterface> planetList = planetLoader.Produce(solarCounter);
             solarSystem = new SolarSystem(planetList); 
             Thread.sleep(pauseDelay);  // pause between change to next solar system
         }
      } 
    catch (InterruptedException e) {
     e.printStackTrace();
    }

  }
    
  public void run() {
    while(true)  {
      solarSystem.run(); // calculations for next animation 
      repaint();
      cycleCount++;
      NextSolarSystemRun(); 
    }
  }
  
  public void paintComponent(final Graphics g)  {
    // clear out previous frame of drawings
    g.setColor( colorBlack ); 
    g.fillRect(0, 0, screenSize, screenSize);
    
    // paint planets in the solar system 
    solarSystem.paint(g, screenMid); 

    // repaint x-y axis lines using dark green
    g.setColor( colorGreen );
    g.drawLine( screenMid, 0, screenMid, screenSize); 
    g.drawLine( 0, screenMid, screenSize, screenMid); 
   }
}

