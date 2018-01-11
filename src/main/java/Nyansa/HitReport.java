package Nyansa;

import java.io.IOException;
import java.util.Collection;

public interface HitReport {
    void process(String fileName)
            throws IOException;

    long getNumReportDays();

    Collection<HitBucket> getHitCounts();
}
