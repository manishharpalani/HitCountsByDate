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
        String[] fields = line.split("\\|");
        if (fields.length >= 2) {
            long epochSeconds = Long.parseLong(fields[0]);
            addHit(ReportDate.getStartOfEpochDay(epochSeconds), fields[1]);
        }
    }

    private void addHit(Long epochDateSec, String entity) {
        hitsForDate.computeIfAbsent(epochDateSec, k -> new HitBucketImpl(ReportDate.asGmtStr(k)))
                .hit(entity);
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
