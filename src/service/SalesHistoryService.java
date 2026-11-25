package service;

import model.Sale;
import java.util.ArrayList;
import java.util.List;

public class SalesHistoryService {
    private List<Sale> sales = new ArrayList<>();
    private int nextSaleId = 1;

    public Sale createSale(List<model.SaleItem> items,
                           double gstRate) {

        Sale sale = new Sale(nextSaleId++, items, gstRate);
        sales.add(sale);
        return sale;
    }

    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    public Sale findSale(int saleId) {
        for (Sale sale : sales) {
            if (sale.getSaleId() == saleId) {
                return sale;
            }
        }
        return null;
    }

    public int getTotalSales() {
        return sales.size();
    }
}