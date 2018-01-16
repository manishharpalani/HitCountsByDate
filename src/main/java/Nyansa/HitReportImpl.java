package Nyansa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

class HitReportImpl implements HitReport {

    private final SortedMap<Long, HitBucket> hitsForDate = new TreeMap<>();

    @Override
    public void process(String fileName)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            processLine(scanner.nextLine().trim());
        }
        scanner.close();
    }

    private void processLine(String line) {
        if (line.length() > 0) {
            long epochSeconds = Long.parseLong(line.split("\\|")[0]);
            addHit(ReportDate.getStartOfEpochDay(epochSeconds), line.split("\\|")[1]);
        }
    }

    private void addHit(Long epochDateSec, String entity) {
        HitBucket hitBucket = hitsForDate.get(epochDateSec);
        if (hitBucket == null) {
            hitBucket = new HitBucketImpl(ReportDate.asGmtStr(epochDateSec));
            hitsForDate.put(epochDateSec, hitBucket);
        }
        hitBucket.hit(entity);
    }

    @Override
    public long getNumReportDays() {
        return hitsForDate.size();
    }

    @Override
    public Collection<HitBucket> getHitCounts() {
        return hitsForDate.values();
    }
}
