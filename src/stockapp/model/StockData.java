package stockapp.model;

/*
 * author 1449773
 */

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StockData {
	
	private final StringProperty date;
    private final DoubleProperty open;
    private final DoubleProperty high;
    private final DoubleProperty low;
    private final DoubleProperty close;
    private final LongProperty volume;
    private final DoubleProperty adjClose;
    

    public StockData(String date, double open, double high, double low, double close, long volume, double adjClose) {
    	this.date = new SimpleStringProperty(date);
    	this.open = new SimpleDoubleProperty(open);
    	this.high = new SimpleDoubleProperty(high);
    	this.low = new SimpleDoubleProperty(low);
    	this.close = new SimpleDoubleProperty(close);
    	this.volume = new SimpleLongProperty(volume);
    	this.adjClose = new SimpleDoubleProperty(adjClose);
    }
    
    public String getDate() {
        return date.get();
    }
    
    public void setDate(String date) {
        this.date.set(date);
    }

    
    public double getOpen() {
        return open.get();
    }
    
    public void setOpen(double open) {
        this.open.set(open);
    }

      
    public double getHigh() {
        return high.get();
    }
    
    public void setHigh(double high) {
        this.high.set(high);
    }
    
    public double getLow() {
        return low.get();
    }
    
    public void setLow(double low) {
        this.low.set(low);
    }

    public double getClose() {
        return close.get();
    }
    
    public void setClose(double close) {
        this.close.set(close);
    }

    public long getVolume() {
        return volume.get();
    }
    
    public void setVolume(long volume) {
        this.volume.set(volume);
    }

    
    public double getAdjClose() {
        return adjClose.get();
    }
    
    public void setAdjClose(double adjClose) {
        this.adjClose.set(adjClose);
    }
}

