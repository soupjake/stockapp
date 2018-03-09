package stockapp.model;

/*
 * author 1449773
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
		
        //code for extracting data from csv files as strings for dataTableView from: http://stackoverflow.com/questions/27324187/java-reading-from-csv-file-and-storing-its-information-into-arraylistclass     	
  	public ArrayList<StockData> getStockData(String csvName) throws IOException{
		
  	  ArrayList<StockData> data = new ArrayList<StockData>();  
  	  String csvFile = "DataFiles/" + csvName + ".csv";
		
          BufferedReader reader = new BufferedReader(new FileReader(csvFile));
          try {
              //skip first line from: http://stackoverflow.com/questions/23236000/bufferedreader-to-skip-first-line
              reader.readLine();
              String line;
              while ((line = reader.readLine()) != null) {
                  //removes comma separator
                  String[] entries = line.split(",");
                  double entries1 = Double.parseDouble(entries[1]);
                  double entries2 = Double.parseDouble(entries[2]);
                  double entries3 = Double.parseDouble(entries[3]);
                  double entries4 = Double.parseDouble(entries[4]);
                  long entries5 = Long.parseLong(entries[5]);
                  double entries6 = Double.parseDouble(entries[6]);
                  StockData dataEntries = new StockData(entries[0], entries1, entries2, entries3, entries4, entries5, entries6);
                  data.add(dataEntries);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          finally {
               reader.close();
          }
          return data;
  	}
}
