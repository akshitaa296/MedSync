package comparators;

import model.Batch;
import java.util.Comparator;

public class BatchExpiryComparator implements Comparator<Batch> {
    @Override
    public int compare(Batch a, Batch b) {
        int result = a.getExpiryDate().compareTo(b.getExpiryDate());

        if (result != 0) return result;

        return a.getBatchNo().compareTo(b.getBatchNo());
    }
}