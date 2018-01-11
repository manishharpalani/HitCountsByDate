package Nyansa;

import java.io.IOException;
import java.util.Collection;

class ReportBuilderImpl implements ReportBuilder {
    private final HitReport report = new HitReportImpl();


    // Report creates a HitBucket for each day (date is calculated using getStartOfEpochDay)
    // Report keeps buckets sorted by the date
    // HashMap contains hash map of URL to hit counts.

    // N = number of lines
    // Assume: N > B * U (given multiple url hits in each bucket)
    //      B = number of HitBuckets - number of days of data (insertion sorted order)
    //      U = average number of unique urls in each bucket (constant, does not grow as N scales)
    //  loading date file takes N*B*LogB
    //
    //  generate report requires sorting the entries in each HitBucket by hit counts
    //      B*LogB*U*LogU = BLogB (U is a constant)

    // Total insertion and report time complexity: N*B*LogB + B*LogB = N*B*LogB
    @Override
    public void load(String inputFile) {
        try {
            report.process(inputFile);
        } catch(IOException ioe) {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    @Override
    public void printReport() {
        Collection<HitBucket> hitCounts = report.getHitCounts();
        for (HitBucket dayHits: hitCounts) {
            printDayReport(dayHits);
        }
    }

    private void printDayReport(HitBucket dayHits) {
        System.out.println(dayHits.getHeader());
        dayHits.getSortedMapByFreq()
                .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Valid file name required as parameter");
        }
        ReportBuilder reportBuilder = new ReportBuilderImpl();
        reportBuilder.load(args[0]);
        reportBuilder.printReport();
    }


}
