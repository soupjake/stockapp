package stockapp.main;

/*
 * author 1449773
 * Java logo png from: https://www.seeklogo.net/technology-logos/download-java-ai-eps-crd-1608.html
 * Report png from: http://www.freeiconspng.com/free-images/report-icon-13320  
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import stockapp.model.Reader;
import stockapp.model.Writer;
import stockapp.model.StockData;

public class StockAppController {
	
	//reading .csv file variable
	public Reader readCSV = new Reader();
	public Writer writeReport = new Writer();
	
	//fxml variables for Stock Details tab
	@FXML
	public Label companyLabel;
	@FXML
	public Label symbolLabel;
	@FXML
	public Label latestStockLabel;
	@FXML
	private ImageView companyImageView;
	@FXML
	private TableView<StockData> companyTableView;
	@FXML
	private TableColumn<StockData, String> dateColumn;
	@FXML
	private TableColumn<StockData, Double> openColumn;
	@FXML
	private TableColumn<StockData, Double> highColumn;
	@FXML
	private TableColumn<StockData, Double> lowColumn;
	@FXML
	private TableColumn<StockData, Double> closeColumn;
	@FXML
	private TableColumn<StockData, Long> volumeColumn;
	@FXML
	private TableColumn<StockData, Double> adjCloseColumn;
	@FXML
	public ListView<String> companyListView = new ListView<String>();
	
	public ObservableList<String> companyList = FXCollections.observableArrayList(
            "Ashtead Group", "Antofagasta", "BAE Systems", "British American Tobacco", "Coca-Cola",
            "Carnival", "Centrica", "Compass Group", "Experian", "EasyJet", "GKN", "Mediclinic International",
            "Provident  Financial", "Paddy Power Betfair", "Prudential", "Persimmon", "Reckitt Benckiser Group",
            "Royal Dutch Shell", "Rolls-Royce Holdings", "Schroders", "Shire", "Sky", "SSE", "St.James's Place",
            "Tesco", "TUI", "Vodafone", "Worldpay");
	
	public ObservableList<String> symbolList = FXCollections.observableArrayList(
			"AHT", "ANTO", "BA", "BATS", "CCH", "CCL", "CNA", "CPG", "EXPN", "EZJ", "GKN", "MDC", "PFG", "PPB",
			"PRU", "PSN", "RB", "RDSA", "RR", "SDR", "SHP", "SKY", "SSE", "STJ", "TSCO", "TUI", "VOD", "WPG");
	
	public ObservableList<StockData> companyData = FXCollections.observableArrayList();
	
	public void getCompanyData() throws Exception{
		try {
			//clears data in companyData if already filled from previous selection
			companyData.clear();
			//ArrayList of stock data created from reading the csv file
			ArrayList<StockData> csvData = readCSV.getStockData(symbolList.get(companyListView.getSelectionModel().getSelectedIndex()));
			//for loop which puts each csv data entry into the companyData ObservableList
			for (StockData entry: csvData){
			companyData.addAll(entry);
			}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}

	private ToggleGroup radioGroup = new ToggleGroup();
	@FXML
	private RadioButton openRB;
	@FXML
	private RadioButton highRB;
	@FXML
	private RadioButton lowRB;
	@FXML
	private RadioButton closeRB;
	@FXML
	private RadioButton volumeRB;
	@FXML
	private RadioButton adjCloseRB;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private LineChart<String, Number> stockChart;
	
	//methods to get different data for Chart
	public void getOpenChart() {
		
		//clear stockChart with old data first
		stockChart.getData().clear();
		
		//putting dates into an ArrayList
		ArrayList<String> dateStrings = getCompanyDateStrings();
		
		//putting opens into an ArrayList		
		ArrayList<Double> openNumbers = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++) {
			double stock = companyData.get(i).getOpen();
			openNumbers.add(stock);
		}
		
		//ArrayList copy that's sorted for yAxis
		ArrayList<Double> openSorted = new ArrayList<Double>(openNumbers);
		Collections.sort(openSorted);
		
		//creating XYChart series
		XYChart.Series<String, Number> openSeries = new XYChart.Series<String, Number>();
		openSeries.setName("Opening Price");
		//reverse ArrayLists around so Chart shows earliest date first code from: http://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
		Collections.reverse(dateStrings);
		Collections.reverse(openNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			openSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), openNumbers.get(i)));
		}
		
		//putting the data into the Chart
		stockChart.getData().add(openSeries);
		//setting the yAxis
		yAxis.setTickUnit(10);
		yAxis.setLowerBound(openSorted.get(0));
		yAxis.setUpperBound(openSorted.get(openSorted.size()-1));
	}
	
	public void getHighChart() {
		
		stockChart.getData().clear();

		ArrayList<String> dateStrings = getCompanyDateStrings();
				
		ArrayList<Double> highNumbers = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++) {
			double stock = companyData.get(i).getHigh();
			highNumbers.add(stock);
		}
		
		ArrayList<Double> highSorted = new ArrayList<Double>(highNumbers);
		Collections.sort(highSorted);
		
		XYChart.Series<String, Number> highSeries = new XYChart.Series<String, Number>();
		highSeries.setName("Opening Price");
		Collections.reverse(dateStrings);
		Collections.reverse(highNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			highSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), highNumbers.get(i)));
		}
		
		stockChart.getData().add(highSeries);
		yAxis.setTickUnit(10);
		yAxis.setLowerBound(highSorted.get(0));
		yAxis.setUpperBound(highSorted.get(highSorted.size()-1));
	}
	
	public void getLowChart() {
		
		stockChart.getData().clear();

		ArrayList<String> dateStrings = getCompanyDateStrings();
				
		ArrayList<Double> lowNumbers = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++) {
			double stock = companyData.get(i).getLow();
			lowNumbers.add(stock);
		}
		
		ArrayList<Double> lowSorted = new ArrayList<Double>(lowNumbers);
		Collections.sort(lowSorted);
		
		XYChart.Series<String, Number> lowSeries = new XYChart.Series<String, Number>();
		lowSeries.setName("Opening Price");
		Collections.reverse(dateStrings);
		Collections.reverse(lowNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			lowSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), lowNumbers.get(i)));
		}
		
		stockChart.getData().add(lowSeries);
		yAxis.setTickUnit(10);
		yAxis.setLowerBound(lowSorted.get(0));
		yAxis.setUpperBound(lowSorted.get(lowSorted.size()-1));
	}
	
	public void getCloseChart() {
		
		stockChart.getData().clear();

		ArrayList<String> dateStrings = getCompanyDateStrings();
				
		ArrayList<Double> closeNumbers = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++) {
			double stock = companyData.get(i).getClose();
			closeNumbers.add(stock);
		}
		
		ArrayList<Double> closeSorted = new ArrayList<Double>(closeNumbers);
		Collections.sort(closeSorted);
		
		XYChart.Series<String, Number> closeSeries = new XYChart.Series<String, Number>();
		closeSeries.setName("Opening Price");
		Collections.reverse(dateStrings);
		Collections.reverse(closeNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			closeSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), closeNumbers.get(i)));
		}
		
		stockChart.getData().add(closeSeries);
		yAxis.setTickUnit(10);
		yAxis.setLowerBound(closeSorted.get(0));
		yAxis.setUpperBound(closeSorted.get(closeSorted.size()-1));
	}
	
	public void getVolumeChart() {
		
		stockChart.getData().clear();

		ArrayList<String> dateStrings = getCompanyDateStrings();
				
		ArrayList<Long> volumeNumbers = new ArrayList<Long>();
		for (int i = 0; i < companyData.size(); i++) {
			long stock = companyData.get(i).getVolume();
			volumeNumbers.add(stock);
		}
		
		ArrayList<Long> volumeSorted = new ArrayList<Long>(volumeNumbers);
		Collections.sort(volumeSorted);
		
		XYChart.Series<String, Number> volumeSeries = new XYChart.Series<String, Number>();
		volumeSeries.setName("Opening Price");
		Collections.reverse(dateStrings);
		Collections.reverse(volumeNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			volumeSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), volumeNumbers.get(i)));
		}
		
		stockChart.getData().add(volumeSeries);
		yAxis.setTickUnit(100000);
		yAxis.setLowerBound(volumeSorted.get(0));
		yAxis.setUpperBound(volumeSorted.get(volumeSorted.size()-1));
	}
	
	public void getAdjCloseChart() {
		
		stockChart.getData().clear();

		ArrayList<String> dateStrings = getCompanyDateStrings();
				
		ArrayList<Double> adjCloseNumbers = new ArrayList<Double>();
		for (int i = 0; i < companyData.size(); i++) {
			double stock = companyData.get(i).getAdjClose();
			adjCloseNumbers.add(stock);
		}
		
		ArrayList<Double> adjCloseSorted = new ArrayList<Double>(adjCloseNumbers);
		Collections.sort(adjCloseSorted);
		
		XYChart.Series<String, Number> adjCloseSeries = new XYChart.Series<String, Number>();
		adjCloseSeries.setName("Opening Price");
		Collections.reverse(dateStrings);
		Collections.reverse(adjCloseNumbers);
		for (int i = 0; i < dateStrings.size(); i++){
			adjCloseSeries.getData().add(new XYChart.Data<String, Number>(dateStrings.get(i), adjCloseNumbers.get(i)));
		}
		
		stockChart.getData().add(adjCloseSeries);
		yAxis.setTickUnit(10);
		yAxis.setLowerBound(adjCloseSorted.get(0));
		yAxis.setUpperBound(adjCloseSorted.get(adjCloseSorted.size()-1));
	}
	
	//method to create ArrayList of company data dates
	public ArrayList<String> getCompanyDateStrings(){
		ArrayList<String> dateStrings = new ArrayList<String>();
		for (int i = 0; i < companyData.size(); i++) {
			String stock = companyData.get(i).getDate();
			dateStrings.add(stock);
		}
		return dateStrings;
	}
	
	//variables and methods for printing report
	@FXML
	private Label printLabel;
	
	public String directory;
	
	//save location file directory code from: http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm	
	public void saveLocation(ActionEvent event) throws Exception{
		try{
			final DirectoryChooser directoryChooser = new DirectoryChooser();
			final File selectedDirectory = directoryChooser.showDialog(null);
			directory = selectedDirectory.toString();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeReport() throws Exception{
		try{
			writeReport.writeData(directory);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Report Generated!");
			alert.setHeaderText(null);
			alert.setContentText("Report generated successfully!");
			alert.showAndWait();
		} catch (Exception e){			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Location Error");
			alert.setHeaderText(null);
			alert.setContentText("No Save Location chosen!");
			alert.showAndWait();
		}
	}
	
    //Initialises the controller class. This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize() {
		//set ListView with companyList
    	companyListView.setItems(companyList);
		
		//ListView event listener and load OpenChart as default
    	companyListView.getSelectionModel().selectedItemProperty()
    	.addListener((observable, oldValue, newValue) -> {
			try {
				getCompanyData();
				companyLabel.setText(companyList.get(companyListView.getSelectionModel().getSelectedIndex()));
				symbolLabel.setText(symbolList.get(companyListView.getSelectionModel().getSelectedIndex())+ ".L");
				latestStockLabel.setText(Double.toString(companyData.get(0).getClose()));
				getOpenChart();
				openRB.setSelected(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
    	  	
        //set TableColumns with data
		dateColumn.setCellValueFactory(new PropertyValueFactory<StockData, String>("date"));
		openColumn.setCellValueFactory(new PropertyValueFactory<StockData, Double>("open"));
		highColumn.setCellValueFactory(new PropertyValueFactory<StockData, Double>("high"));
		lowColumn.setCellValueFactory(new PropertyValueFactory<StockData, Double>("low"));
		closeColumn.setCellValueFactory(new PropertyValueFactory<StockData, Double>("close"));
		volumeColumn.setCellValueFactory(new PropertyValueFactory<StockData, Long>("volume"));
		adjCloseColumn.setCellValueFactory(new PropertyValueFactory<StockData, Double>("adjClose"));
		
		//set dataTable with companyData
		companyTableView.setItems(companyData);
		
		//setting RadioButtons to toggle group
		openRB.setToggleGroup(radioGroup);
		highRB.setToggleGroup(radioGroup);
		lowRB.setToggleGroup(radioGroup);
		closeRB.setToggleGroup(radioGroup);
		volumeRB.setToggleGroup(radioGroup);
		adjCloseRB.setToggleGroup(radioGroup);
    }
}