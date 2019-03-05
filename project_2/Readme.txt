  In the program, I build Matrix A,B,pi, and use that to complete class forward and class Viterbi. I use Matrix class Jama and consider numerical issues in two parts. Finally, my accuracy is 89% . 

  In this phase 2, at first, I change the method of normalization on forward and backward(write in phase 2) and mle, and my accuracy is 93% compared with 89% in phase 1. In this phase, I finished the backward and em. Finally, my accuracy is 65% after 30 iteration and the log.txt is finished.
  The time is 3min.


  In this phase 3, mu is set at line 137 and line 723. 
  I modify this code and the time to run small dataset 30 times is 40s. The time to run big dataset 30 times is 5150.264s, about 50 minutes. 
  I start to deal with big dataset is at 858 line. If we want to just get the result of small dataset, we can delete 858 line to 930 line of stop at 855 line.
  If we want to modify the time of iteration, the small dataset is at 833 line, and the big dataset is at 908 line.  
  And I delete my result of prediction.txt for big dataset because I can't deliver my zip to course site because it is too big.
  And at the 12 line in evaluator.java, we should modify path "./results/p3/predictionssmall.txt" so we could get the result.