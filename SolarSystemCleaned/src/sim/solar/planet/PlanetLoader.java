package sim.solar.planet;

//import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;  
import com.opencsv.CSVReader; 

public class PlanetLoader {

    //private static String planetConfigFile = "solarconfig.csv";
    
    
    
    //TODO: use the row value to select just one row of the solarconfig.csv to be used for each display 
    
    public List<PlanetInterface> Produce(int row) {
        
        List<PlanetInterface> planetList = new ArrayList<>();
        
        
        //TODO: call the ReadPlanetConfig method and have it return a collection of the data it found
        List<Integer> solar=ReadPlanetConfig("solarconfig.csv",row);
        //TODO: use the 'row' selection to utilize only the selected row from the CSV file
        
        //TODO: store the data from solarconfig.csv into these eleven variables:
        /*int numplanets = 90;   
        int orbit      = 240;  
        int increment  = 1;    
        int angleinc   = 5;    
        int planetsize = 10;   
        int redbase    = 240;  
        int greenbase  = 240;  
        int bluebase   = 60;   
        int redinc     = -2;   
        int greeninc   = -2;   
        int blueinc    = 2; */
        
 
        int numplanets = solar.get(0);   
        int orbit      = solar.get(1);  
        int increment  = solar.get(2);    
        int angleinc   = solar.get(3);    
        int planetsize = solar.get(4);   
        int redbase    = solar.get(5);  
        int greenbase  = solar.get(6);  
        int bluebase   = solar.get(7);   
        int redinc     = solar.get(8);   
        int greeninc   = solar.get(9);   
        int blueinc    = solar.get(10);
      // Storing data from config file.
         
        
        int angle     = 0;
        int red ;
        int green;
        int blue; 
        
        for (int i=0; i<numplanets; i++) {
            angle    = angle + angleinc;   // controls offset between planets
            red      = redbase   + redinc*i;   // planet color 
            green    = greenbase + greeninc*i;   // planet color
            blue     = bluebase  + blueinc*i;   // planet color
                        
            try{
            if( blue<0 || blue>255||red <0 || red>255 || green<0 || green >255) //  0 to 255 
            continue;// continue the program to handle exception 
            planetList.add(new Planet (angle, orbit, increment, planetsize, red, green, blue));
            }
            catch(IllegalArgumentException exe)
            {
            System.out.println(exe);
            }
         }
         
        return planetList; 
   }
      
   
   
   //TODO: upgrade this method as necessary to return the data in a form that can be used by Produce method
   
   private  List<Integer> ReadPlanetConfig(String fileName,int row)  {  
      CSVReader reader = null; 
       List<Integer> solarUpdate=new ArrayList<>();


      try {  

         reader = new CSVReader(new FileReader(fileName));
         String [] nextLine;  
         nextLine = reader.readNext();  // read the header line 
                          
         //TODO: figure out what to do with the header information if its needed
         for ( String token : nextLine)  {  
            //TODO: figure out what to do with the header information if its needed
         }
           int count=0;
         //TODO: take each token and copy them into some type of collection storage, ArrayList, HashMap, etc 
         while ((nextLine = reader.readNext()) != null) 
         {  
            if(count==row)
            {
            for ( String token : nextLine)  
            {
            int value=Integer.parseInt(token);
            solarUpdate.add(value);
               //TODO: take each token and copy them into some type of collection storage, ArrayList, HashMap, etc 
            }
            }
            count++;
         }
         reader.close(); 
      }  
      catch (Exception e) {  
         e.printStackTrace();  
      }
      return solarUpdate;  
   } 
}

