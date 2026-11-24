package ui;

import service.StockService;
import util.SampleData;

public class Main {
    public static void main(String[] args) {
        StockService stock = new StockService();

        SampleData.load(stock);

        new MenuHandler(stock).start();
    }
}