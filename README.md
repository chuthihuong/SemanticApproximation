# Semantic Approximation for Reducing Code Bloat in Genetic Programming
This code provides a Java implementation of Semantic Approximation as described in "Semantic Approximation for Reducing Code Bloat in Genetic Programming" by Thi Huong Chu et al.

  # Methods
+ GP: Standard GP,
+ SA: Semantic Approximation,
+ DA: Desired Approximation,
+ RDO: Crossover-random desired operator(RDO),
+ PP: Prune and Plant operator.
  # Evolutionary Parameter Values:
You can set evolutionary parameter values in file GP/Common/Const.java. Here are some basic parameters you can set in the file:
+ POPSIZE:  Population size,
+ NUMGEN: Generations, 
+ TOURSIZE: Tournament size,
+ PCROSS: Crossover probability,
+ PMUTATE: Mutation probability.

  # Prolem Paramater values:
You set up the information of the problem in file GP/Common/Const.java:
+ PROBLEM: The name of the problem,
+ NUMVAR: The number of features,
+ NUMFITCASE: The number of samples in training data,
+ NUMFITTEST: The number of samples in testing data.
  # Supplement: 
The supplement results in the paper "Semantic Approximation for Reducing Code Bloat in Genetic Programming" by Thi Huong Chu et al.
