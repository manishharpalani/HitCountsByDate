package Nyansa;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface HitReport {
    void process(String fileName)
            throws FileNotFoundException;

    long getNumReportDays();

    Collection<HitBucket> getHitCounts();
}
