# Steps to Configure/Run with sample input.txt

   1. Create Application Target in Edit Configuration...
   2. Set Main Class: _Nyansa.ReportBuilderImpl_
   3. Set Program Argument: resources/input.txt 

# Program Output
    08/08/2014 GMT
    www.facebook.com 2
    www.google.com 2
    news.ycombinator.com 1
    08/09/2014 GMT
    www.nba.com 3
    sports.yahoo.com 2
    www.cnn.com 1
    08/10/2014 GMT
    www.twitter.com 1

# Details on O(N) Complexity
     Report creates a HitBucket for each day (date is calculated using getStartOfEpochDay)
     Report keeps buckets sorted by the date
     HashMap contains hash map of URL to hit counts.

     N = number of lines
     Assume: N > B * U (given multiple url hits in each bucket)
          B = number of HitBuckets - number of days of data (insertion sorted order)
          U = average number of unique urls in each bucket (constant, does not grow as N scales)

    Space Complexity: B * U = B

    Time Complexity:
      loading date file takes N*B*LogB

      generate report requires sorting the entries in each HitBucket by hit counts
          B*LogB*U*LogU = BLogB (U is a constant)
