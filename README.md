# HitCountsByDate

Details on O(N) Complexity:
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

Problem:

You’re given an input file. Each line consists of a timestamp (unix epoch in seconds) and a url separated by ‘|’ (pipe operator). The entries are not in any chronological order. Your task is to produce a daily summarized report on url hit count, organized daily (use GMT) with the earliest date appearing first. For each day, you should display the number of times each url is visited in the order of highest hit count to lowest count. Your program should take in one command line argument: input file name. The output should be printed to stdout. You may assume that the cardinality of hit count values and the number of days are much smaller than the number of unique URLs. You may also assume that number of unique URLs can fit in memory. 


input.txt

1407564301|www.nba.com
1407478021|www.facebook.com
1407478022|www.facebook.com
1407481200|news.ycombinator.com
1407478028|www.google.com
1407564301|sports.yahoo.com
1407564300|www.cnn.com
1407564300|www.nba.com
1407564300|www.nba.com
1407564301|sports.yahoo.com
1407478022|www.google.com
1407648022|www.twitter.com


Output

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


Correctness, efficiency (speed and memory) and code cleanliness will be evaluated. Please provide a complexity analysis in Big-O notation for your program along with your source. 
