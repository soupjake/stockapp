package stockapp.model;

/*
 * author 1449773
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import stockapp.model.Reader;

public class Writer {

	public void writeData(String directory)throws Exception{
		
	Reader readCSV = new Reader();

	//create ArrayLists of company names and symbols
	ArrayList<String> companyList = new ArrayList<String>();
	companyList.addAll(Arrays.asList("Ashtead Group", "Antofagasta", "BAE Systems", "British American Tobacco", "Coca-Cola",
            "Carnival", "Centrica", "Compass Group", "Experian", "EasyJet", "GKN", "Mediclinic International",
            "Provident  Financial", "Paddy Power Betfair", "Prudential", "Persimmon", "Reckitt Benckiser Group",
            "Royal Dutch Shell", "Rolls-Royce Holdings", "Schroders", "Shire", "Sky", "SSE", "St.James's Place",
            "Tesco", "TUI", "Vodafone", "Worldpay"));
	
	ArrayList<String> symbolList = new ArrayList<String>();
	symbolList.addAll(Arrays.asList("AHT", "ANTO", "BA", "BATS", "CCH", "CCL", "CNA", "CPG", "EXPN", "EZJ", "GKN", "MDC", "PFG", "PPB",
			"PRU", "PSN", "RB", "RDSA", "RR", "SDR", "SHP", "SKY", "SSE", "STJ", "TSCO", "TUI", "VOD", "WPG"));
	
	//user File.separator so able to be used across different operating systems
	FileWriter writer = new FileWriter(directory + File.separator + "StockReport.txt");
	BufferedWriter output = new BufferedWriter(writer);
	
	for (int sequenceNumber = 0; sequenceNumber < companyList.size(); sequenceNumber++){
		//read csv files to get data
		ArrayList<StockData> companyData = readCSV.getStockData(symbolList.get(sequenceNumber));
		
		//create ArrayList of high doubles
		ArrayList<Double> highDoubles = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++){
			highDoubles.add(companyData.get(i).getHigh());
		}
		
		//create ArrayList of low doubles
		ArrayList<Double> lowDoubles = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++){
			lowDoubles.add(companyData.get(i).getLow());
		}
		
		//create ArrayList of close doubles
		ArrayList<Double> closeDoubles = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++){
			closeDoubles.add(companyData.get(i).getClose());
		}
		
		//find index in companyData which has the highest stock price from: http://stackoverflow.com/questions/1806816/java-finding-the-highest-value-in-an-array
		int maxIndex = 0;
		for (int i = 1; i < highDoubles.size(); i++){
			Double newNumber = highDoubles.get(i);
			if ((newNumber > highDoubles.get(maxIndex))){
				maxIndex = i;
			}
		}
		//find index in companyData which has the lowest stock price
		int minIndex = 0;
		for (int i = 1; i < lowDoubles.size(); i++){
			Double newNumber = lowDoubles.get(i);
			if ((newNumber < lowDoubles.get(minIndex))){
				minIndex = i;
			}
		}
		
		//variables to print
		String highest = companyData.get(maxIndex).getDate();
		String lowest = companyData.get(minIndex).getDate();
		//find average close price from: http://stackoverflow.com/questions/10791568/calculating-average-of-an-array-list
		Double average = closeDoubles.stream().mapToDouble(val -> val).average().getAsDouble();
		String close = Double.toString(companyData.get(0).getClose());
		try{
			output.write("Number: " + (sequenceNumber+1) + System.getProperty("line.separator"));
			output.write("Stock Symbol: " + symbolList.get(sequenceNumber) + ".L" + System.getProperty("line.separator"));
			output.write("Company Name: " + companyList.get(sequenceNumber) + System.getProperty("line.separator"));
			output.write("Highest: " + highest + System.getProperty("line.separator"));
			output.write("Lowest: " + lowest + System.getProperty("line.separator"));
			output.write("Average Close: " + String.format("%.2f", average) + System.getProperty("line.separator"));
			output.write("Close: " + close + System.getProperty("line.separator"));
			output.write(System.getProperty("line.separator"));

		} catch (Exception e){
			e.printStackTrace();
		}
	} output.close();
	}
}