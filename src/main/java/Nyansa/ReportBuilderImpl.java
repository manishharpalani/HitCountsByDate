package Nyansa;

import java.io.FileNotFoundException;
import java.util.Collection;

import static java.lang.System.exit;

/*
HitReport creates a HitBucket indexed by getStartOfEpochDay (hour, min, sec of timestamp are discarded
HitBucket keeps the url in 2 maps
    1. url:frequency
    2. frequency;set of urls
Both maps are updated, as needed, for each hit

    N = number of lines
    Space Complexity: O(N)

    Time Complexity:
    loading date file takes O(N)

Generating report requires getting the HitBuckets in order from most recent to the oldest
For each bucket we get the urls in order from most frequent to least frequent (use frequency;set of urls)

Time Complexity: O(N)
*/
class ReportBuilderImpl implements ReportBuilder {
    private final HitReport report = new HitReportImpl();

    @Override
    public void load(String inputFile) {
        try {
            report.process(inputFile);
        } catch (FileNotFoundException ioe) {
            System.err.println(String.format("File not found: '%s'. Exiting.\n", inputFile));
        }
    }

    @Override
    public void printReport() {
        Collection<HitBucket> hitCounts = report.getHitCounts();
        for (HitBucket dayHits : hitCounts) {
            printDayReport(dayHits);
        }
    }

    private void printDayReport(HitBucket dayHits) {
        System.out.println(dayHits.getHeader());

        for (long hits = dayHits.getTopHitCount(); hits >= 1; --hits) {
            for (String url : dayHits.getItemsWithHitCount(hits)) {
                System.out.println(String.format("%s %d", url, hits));
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Missing parameter - input file name required. Exiting.");
            exit(0);
        }
        ReportBuilder reportBuilder = new ReportBuilderImpl();
        reportBuilder.load(args[0]);
        reportBuilder.printReport();
    }
}
