package DAD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;






import Common.Class_Random;





public class Population extends Class_Random {
	
	individual[]	oldpop, newpop;	// array contains population											
	int				poplen;			//population size											
	double			pcross, pmutate, pcopy;								
	long				ncross, nmutate;
	static int				gltermcard;										
	static int				glfcard;
	static int				gltcard;
	term[]			glterm		= new term[MAXFUNCTION + MAXTERMINAL];
	term[]			glfunction	= new term[MAXFUNCTION];
	term[]			glterminal	= new term[MAXTERMINAL];
	sample[]	fitcase			= new sample[NUMFITCASE];
	sample[]	fittest			= new sample[NUMFITTEST];
	byte		SuccPredicate	= FALSE;
	int				generation, gen = 0;
	individual[]	bestcurrent;
	double[]			average;
	double[]			averageSize;
//	int[][]				popSize;
	
	
	int[][]				AUC;
	
//	private node[] treelib;
//	private double[][] SemanticsTreelib;
	
	
//	public double[] constructiveRate;
//	public double[] semanticDistance;
//
//
//	public double[] avgBeforeFitness;
	double[] targetSemantics = new double[NUMFITCASE];
	
	
//	public double[] bestBeforeFitness;
//	public double[] avgDecreaseFitness;
//	public double[] avgDecreaseAllFitness;
	
	int g_numOfBetter;
//	double DecreaseFitness;
//	double DecreaseAllFitness;
	int g_numTrunc;
	int g_numTruncFalse;
	
//	public double[] avgFitnessNotPrune;
//	public double[] avgFitnessBeforePrune;
//	public double[] avgFitnessAfterPrune;
//	
//	public double[] avgFitnessAllPopBefore;
//	public double[] avgFitnessAllPopAfter;
//	
//	
//	public double[] TheBestFitnessNotPrune;
//	public double[] TheBestFitnessPrune;
//	
//	public int[] TheBestInNotPrune;	
//	// Trung binh tren fitness truoc khi tia tot hon
//	public int[] TheBestAvgInAllPopBefore;
//	// Trung binh tren phaanf khong tia tot hon phan tia
//	public int[] TheBestAvgInNotPrune;
	
	public int [] numTruncSuccess;
	public int [] numTruncFalse;
	
	
	public int [] numberTrunc;
	public double [] TimeTrunc;
	public double [] TimeCaculateFitness;
	public double [] TimePartTrunc;
	
	int g_numberTrunc;
	double  g_TimeTrunc;
	double g_TimePartTrunc;
	
	double g_TimeCaculateFitness;
	
	
// **************************************************
//Class contain input
	class sample {
		double x[]=new double[NUMVAR];
		double	y;
	}
//Class contain function and terminal 
	class term {
		String	name;
		byte	arity;
	};

	public Population(long seed) {
		super.Set_Seed(seed);
	
		gltermcard = 0;
		glfcard = 0;
		gltcard = 0;
		//initialized terms
		for(int i = 0; i < MAXFUNCTION + MAXTERMINAL; i++)
			glterm[i] = new term();
		for(int i = 0; i < MAXTERMINAL; i++)
			glterminal[i] = new term();
		for(int i = 0; i < MAXFUNCTION; i++)
			glfunction[i] = new term();
		
		AddTerm("add", (byte) 2);
		AddTerm("sub", (byte) 2);
		
		AddTerm("mul", (byte) 2);
		AddTerm("div", (byte) 2);
		
		AddTerm("sin", (byte) 1);
		AddTerm("cos", (byte) 1);
//		AddTerm("ep", (byte) 1);
//		AddTerm("log", (byte) 1);
		//AddTerm("sqrt", (byte) 1);
		//AddTerm("sqr", (byte) 1);
		for(int i=0;i<NUMVAR;i++){				
    		AddTerm("X"+String.valueOf(i+1), (byte) 0);
		}
		
		SwapTerm(100);		
		poplen = POPSIZE;		
		oldpop = new individual[poplen];
		assert (oldpop != null);
		newpop = new individual[poplen];
		assert (newpop != null);
		for(int i = 0; i < poplen; i++) {
			oldpop[i] = new individual();
			newpop[i] = new individual();
		}
		
		pcross = PCROSS;
		pmutate = PMUTATE;
		
		ncross = 0;
		nmutate = 0;		
		generation = NUMGEN;		
		bestcurrent = new individual[generation];
		assert (bestcurrent != null);
		for(int i=0; i<generation; i++)
			bestcurrent[i]=new individual();		
		average = new double[generation];			
		assert (average != null);
		
		
		averageSize = new double[generation];			
		assert (averageSize != null);
		
		
//		treelib = new node[TREELIBSIZE];
//		assert (treelib != null);		
//		for(int i = 0; i < TREELIBSIZE; i++) {
//			treelib[i] = new node();			
//		}
//		
//		SemanticsTreelib=new double[TREELIBSIZE][NUMFITCASE];
//		assert (SemanticsTreelib != null);
		
//		bestBeforeFitness=new double[NUMGEN];
//		avgDecreaseFitness=new double[NUMGEN];
//		avgDecreaseAllFitness=new double[NUMGEN];
		
//		avgFitnessNotPrune=new double[NUMGEN];
//		avgFitnessBeforePrune=new double[NUMGEN];
//		avgFitnessAfterPrune=new double[NUMGEN];
//		TheBestFitnessNotPrune=new double[NUMGEN];
//		TheBestFitnessPrune=new double[NUMGEN];
//		
//		
//		avgFitnessAllPopBefore=new double[NUMGEN];
//		avgFitnessAllPopAfter=new double[NUMGEN];
//		
//		
//		TheBestInNotPrune=new int[NUMGEN];
//		
//		TheBestAvgInAllPopBefore=new int[NUMGEN];		
//		TheBestAvgInNotPrune=new int[NUMGEN];
		numTruncSuccess=new int [NUMGEN];
		numTruncFalse=new int [NUMGEN];
		
		numberTrunc=new int [NUMGEN];
		TimeTrunc=new double[NUMGEN];
		TimePartTrunc=new double[NUMGEN];
		
		TimeCaculateFitness=new double[NUMGEN];
		
//		avgBeforeFitness=new double[NUMGEN];
//		popSize = new int[generation][poplen];			
//		assert (popSize != null);
	
		
		AUC = new int[2][NUMFITTEST];			
		assert (AUC != null);
		
		
		SuccPredicate = FALSE;
		SetFitCase();
		SetFitTest();
		
//		genTreelibWithDistinctSemantics(TREELIBSIZE, TREELIB_MAXDEPTH);		
//		System.err.println("Generate treelib done!");
		for(int i = 0; i < NUMFITCASE; i++)
			targetSemantics[i] = fitcase[i].y;
	}
	
	// Add function and terminal
	void AddTerm(String name, byte arity){
		glterm[gltermcard].arity = arity;
		glterm[gltermcard].name = name;
		gltermcard++;
		if(arity == 0) {//terminal
			glterminal[gltcard].arity = arity;
			glterminal[gltcard].name = name;
			gltcard++;
		} else {// function
			glfunction[glfcard].arity = arity;
			glfunction[glfcard].name = name;
			glfcard++;
		}
	}
//Print term 
	void PrintTerm(){
		int i;
		for(i = 0; i < gltermcard; i++) {
			System.out.println("--------TERM " + i + "---------");
			System.out.println("NAME:" + glterm[i].name);
			System.out.println("ARITY:" + (int) glterm[i].arity);
		}
	}
	// Swap term times
	void SwapTerm(int times){
		int i, j, k;
		term temp;
		assert (gltermcard >= 2);
		for(i = 0; i < times; i++) {
			j = IRandom(0, gltermcard - 1);
			k = IRandom(0, gltermcard - 1);
			temp = glterm[j];
			glterm[j] = glterm[k];
			glterm[k] = temp;
		}
	}

	// Individual
	class individual {
		node		chrom;	//  chrom of individual
		int			size, height;	// size and heigh of individual
		double		branch;			
		double		fitness;	
		double    oldfitness;
		byte evaluated;
		
		double[] semanticTraining;

       public individual() {
			size = 0;
			height = 0;
			branch = 0;
			fitness = 0;
			oldfitness=0;
			evaluated=FALSE;
			chrom = new node();
			semanticTraining = new double[NUMFITCASE];
			
		}
//copy Individual
		public void CopyIndividual(individual t, byte copychrom){
			if(copychrom == TRUE)//truong hop copy den vung nho khac 
				this.chrom = chrom.CopyNode(t.chrom);
			else 
				//truong hop trung vung nho
				this.chrom = t.chrom;			

			this.size = t.size;
			this.height = t.height;
			this.fitness = t.fitness;
			this.branch = t.branch;
			this.oldfitness=t.oldfitness;
			this.evaluated=t.evaluated;
			
			
			this.semanticTraining = t.semanticTraining.clone();
		}
		//Delete individual
		void DeleteChrom(node t){
			node q, p;
			
			if(t!=null)
			{
			if(t.children==null)
				t=null;
			else
			{
				p=t.children;
				while(p!=null)
				{
					q=p.sibling;
					DeleteChrom(p);
					p=q;
				}
				t=null;
			}
			}
			t=null;
		}
//Display Individual 
		void DisplayIndividual(){
			System.out.println("-----------------------------");
			System.out.println("Genotype Structure:");			
			this.chrom.DisplaySTree(this.chrom);
			System.out.println("Size:" + this.size);
			System.out.println("Height:" + this.height);
			System.out.println("Branching:" + this.branch);
			System.out.println("Fitness:" + this.fitness);
		}
		
		void DisplayIndividualChrom(){
			this.chrom.DisplaySTree(this.chrom);			
		}
		
//		public boolean isBetterThan(individual individual)
//		{
//			if (ComputeRF(this) < ComputeRF(individual))
//				return true;
//			else
//				return false;
//		}
		
		public boolean isBetterThan(individual individual)
		{
			double tm;
			if(this.evaluated==FALSE)
			{   tm=ComputeRF(this);
				this.fitness = tm;
				this.oldfitness = tm;
				this.evaluated=TRUE;
			}
			if(individual.evaluated==FALSE)
			{   tm=ComputeRF(individual);
				individual.fitness = tm;
				individual.oldfitness = tm;
				individual.evaluated=TRUE;
			}
			
			if (this.oldfitness <individual.oldfitness)
				return true;
			else
				return false;
		}
		
		public double getSemanticDistance(individual individual)
		{
			double sd = 0.0;
			
			for(int i = 0; i < this.semanticTraining.length; i++)
			{
				if(Math.abs(this.semanticTraining[i]) > 10000
						|| Math.abs(individual.semanticTraining[i]) > 10000)
					continue;
				
				sd += Math.abs(this.semanticTraining[i]-individual.semanticTraining[i]);
			}
			
			return sd/NUMFITCASE;
		}
		
	}
	
	
//Create tree with Grow 
	node GrowthTreeGen(int maxdepth){
		int i;
		byte a;
		node t, p;
		if(maxdepth == 0) // to the limit then choose a terminal
		{
			i = IRandom(0, gltcard - 1);
			t = new node(glterminal[i].name, VOIDVALUE);
			return t;
		} 
		else // choosing from function and terminal
		 {
			i = IRandom(0, gltermcard - 1);
			t = new node(glterm[i].name, VOIDVALUE);
			if(glterm[i].arity > 0) // if it is function
			{
				t.children = GrowthTreeGen(maxdepth - 1);
				p = t.children;
				for(a = 1; a < glterm[i].arity; a++) {
					p.sibling = GrowthTreeGen(maxdepth - 1);
					p = p.sibling;
				}
				p.sibling = null;
			}
			return t;
		}
	}
//Create tree with Full tree
node FullTreeGen(int maxdepth){
		int i;
		byte a;
		node t, p;
		if(maxdepth == 0) // to the limit then choose a terminal
		{
			i = IRandom(0, gltcard - 1);
			t = new node(glterminal[i].name, VOIDVALUE);
			return t;
		} 
		else // choosing from function
		{
			i = IRandom(0, glfcard - 1);
			t = new node(glfunction[i].name, VOIDVALUE);
			t.children = FullTreeGen(maxdepth - 1);
			p = t.children;
			for(a = 1; a < glfunction[i].arity; a++) {
				p.sibling = FullTreeGen(maxdepth - 1);
				p = p.sibling;
			}
			p.sibling = null;
			return t;
		}
	}

//Initialize population
	void RampedInit(int depth, double percentage)
	{
		int i, j;
		byte k;
		node t, p;
		for(i = 0; i < poplen; i++) {
			// choose randomly from the function set
			j = IRandom(0, glfcard - 1);
			t = new node(glfunction[j].name, VOIDVALUE);
			if(Next_Double() < percentage)// Growth
			{
				t.children = GrowthTreeGen(depth - 1);
				p = t.children;
				for(k = 1; k < glfunction[j].arity; k++) {
					p.sibling = GrowthTreeGen(depth - 1);
					p = p.sibling;
				}
				p.sibling = null;
			} 
			else 
			{
				t.children = FullTreeGen(depth - 1);
				p = t.children;
				for(k = 1; k < glfunction[j].arity; k++) {
					p.sibling = FullTreeGen(depth - 1);
					p = p.sibling;
				}
				p.sibling = null;
			}
			oldpop[i].chrom = t;
			oldpop[i].size = t.GetSizeNode(t, TRUE);
			oldpop[i].height = t.GetHeightNode();
			oldpop[i].branch = t.GetAVGNode(t);
		}
	}
//
	void AdjustFitness(){
		int i;
		for(i = 0; i < poplen; i++)
			oldpop[i].fitness = 1 / (1 + oldpop[i].fitness);
	}

	void NormalizeFitness(){
		double sum = 0;
		int i;
		for(i = 0; i < poplen; i++)
			sum += oldpop[i].fitness;
		for(i = 0; i < poplen; i++)	{
			oldpop[i].fitness = oldpop[i].fitness / sum;		
		}
	}
// Tournement Selection
	int TourSelect(int size){
		int i, j, pos = 0;
		double max = -1000;
		assert (size <= MAXTOUR);
		for(i = 0; i < size; i++) {
			j = IRandom(0, poplen - 1);			
			if(oldpop[j].fitness > max) {
				max = oldpop[j].fitness;
				pos = j;
			}
		}		
		return pos;
	}
	
	  // Child of left or right
    private int getPosition(node a, node n)
    {
    	node childOfa = n;
    	while(childOfa.parent != a)
    		childOfa = childOfa.parent;
    	
    	if (a.children == childOfa)
    		return 0;
    	else return 1;
    }
    
  
    private node getChild(node a, node n)
    {
    	node childOfa = n;
    	while(childOfa.parent != a)
    		childOfa = childOfa.parent;    	
    	return childOfa;
    }
	
    
 public ArrayList<Double>[] semanticBackPropagationAll(double[] targetSemantics, node p, node n)
   {
	    	
	
	ArrayList<Double>[] out = new ArrayList[NUMFITCASE];
	for (int i = 0; i < targetSemantics.length; i++)
	{   out[i]=new 	 ArrayList<Double>();	    		
		double t = targetSemantics[i];    		
		out[i].add(t); 	
	
	}
	node a = p;
	
	while(a != n)
	{
//	
		int k = getPosition(a, n);		
		out=invertAll(a, k, out);//		
		a = getChild(a, n);
	}					
	
	return out;
	
}

 public ArrayList<Double>[] invertAll1(node a, int k, ArrayList<Double>[] out)
   {
   	ArrayList<Double>[] semantics = new ArrayList[NUMFITCASE];;
   	
   	for(int i=0; i<NUMFITCASE; i++)	{
   		semantics[i]= new ArrayList<Double>();
   	}
   	
   	
   	if(k==0) 
   	{
   		if (a.name == "add")
   		{	double[] c2 = ComputeNew(a.children.sibling);
	        	for(int i=0; i<NUMFITCASE; i++)	{	
	        		if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        		semantics[i].add(o - c2[i]);// = targetSemantics[i] - semanticsChild2[i];	
	        		}
   			}
	        	return semantics;
   		}
   		if (a.name == "sub")
   		{	double[] c2 = ComputeNew(a.children.sibling);
   			for(int i=0; i<NUMFITCASE; i++)	{
   				if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        		semantics[i].add(o + c2[i]);}
   			}
   			return semantics;
   		}
   		if (a.name == "mul")
   		{   
   			double[] c2 = ComputeNew(a.children.sibling); 
   			for(int i=0; i<NUMFITCASE; i++)	{    				
   				if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){	
						if(c2[i] != 0)
							semantics[i].add(o/c2[i]);
						else {
								semantics[i].add(STAR);
						}
		        	}
   			}
   			return semantics;
   		}
   		if (a.name == "div")
   		{
       		double[] c2 = ComputeNew(a.children.sibling); 
       		for(int i=0; i<NUMFITCASE; i++)	{ 
       			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
						if(Math.abs(c2[i]) != Double.MAX_VALUE)
						{
							semantics[i].add(o * c2[i]);
						}
						else
						{					
							semantics[i].add(STAR);							
						}
	        		}
       		}
   			return semantics;
   		}
   		
   	}
   	else //k==1
   	{
   		if (a.name == "add")
   		{
       		double[] c1 = ComputeNew(a.children); 
       		for(int i=0; i<NUMFITCASE; i++)	{ 
	        			if(out[i].contains(null))
		    				continue;
		        		for(double o:out[i]){
	        			semantics[i].add(o-c1[i]);
       			}
       		}
   			return semantics;
   		}
   		if (a.name == "sub")
   		{
   			double[] c1 = ComputeNew(a.children); 
       		for(int i=0; i<NUMFITCASE; i++)	{   
       			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        			semantics[i].add(c1[i]-o);
	        		}
       		}
   			return semantics;
   		}
   		if (a.name == "mul")
   		{
   			double[] c1 = ComputeNew(a.children); 
       		for(int i=0; i<NUMFITCASE; i++)	{   
       			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
						if(c1[i] != 0)
							semantics[i].add(o/c1[i]);
						else  {
							semantics[i].add(STAR);
							
						}
						
	        		}
       		}
   			return semantics;
   		}
   		if (a.name == "div")
   		{
   			double[] c1 = ComputeNew(a.children); 
       		for(int i=0; i<NUMFITCASE; i++)	{  
       			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
					if(o != 0)
						semantics[i].add(c1[i]/o);
					else 
						semantics[i].add(STAR);
					 }
       		}
   			return semantics;
   		}
   		
   	}
   	// exp
   	if (a.name == "ep")
		{   for(int i=0; i<NUMFITCASE; i++)	{ 
			if(out[i].contains(null))
				continue;
   		for(double o:out[i]){
				if(o >= 0)
					semantics[i].add(Math.log(o));
				else
					semantics[i].add(STAR);
	    		}
			}
			return semantics;
		}
   	
   	if (a.name == "log")
		{	
   		for(int i=0; i<NUMFITCASE; i++)	{ 
   			if(out[i].contains(null))
   				continue;
       		for(double o:out[i]){
	    			semantics[i].add(-Math.exp(o));
	    			semantics[i].add(Math.exp(o));
   			}
			}
			return semantics;
		}

   	if (a.name == "sin")
		{
   		for(int i=0; i<NUMFITCASE; i++)	{ 
   			if(out[i].contains(null))
   				continue;
		        		for(double o:out[i]){
			    		if (Math.abs(o) <= 1)	    		{
			    			semantics[i].add(Math.asin(o) - 2 * Math.PI);
			    			semantics[i].add(Math.asin(o));
			    		}
			    		else{
			    			semantics[i].add(STAR);
			    			}
		        }
   		}
			return semantics;
		}
   	
   	if (a.name == "cos")
		{
   		for(int i=0; i<NUMFITCASE; i++)	{ 
   			if(out[i].contains(null))
   				continue;
       		for(double o:out[i]){
		    		if (Math.abs(o) <= 1)
		    		{
		    			semantics[i].add(Math.acos(o) - 2 * Math.PI);
		    			semantics[i].add(Math.acos(o));
		    		}
		    		else{
		    			semantics[i].add(STAR);
		    		}
       		}
   		}
			return semantics;
		}

   	if (a.name == "sqrt")
		{
   		for(int i=0; i<NUMFITCASE; i++)	{ 
   			if(out[i].contains(null))
   				continue;
	        		for(double o:out[i]){
					if (o >= 0)
						semantics[i].add(o * o);
					else
						semantics[i].add(STAR);
       		}
   		}
			return semantics;
		}

   	if (a.name == "sqr")
		{
   		for(int i=0; i<NUMFITCASE; i++)	{ 
   			if(out[i].contains(null))
   				continue;
	        		for(double o:out[i]){
		    		if (o>= 0)
		    		{
		    			semantics[i].add(Math.sqrt(o));
		    			semantics[i].add(-Math.sqrt(o));
		    		}
		    		else {
		    			semantics[i].add(STAR);
					}
       		}
			}
			return semantics;
		}
   	
   	//
   	
		return semantics;
   	
   }




//	 private void genTreelibWithDistinctSemantics(int treelibsize, int depth)
//	 {
//
//	        node randomTree; 
//
//	        int i,j;
////	        kdtreelib = new KDTree<node>(NUMFITCASE);
////	        while(kdtreelib.size() < treelibsize)
//	        for(i=0; i<treelibsize; i++){
//	        	randomTree = GrowthTreeGen(depth);	        
//	        	double[] semantic = ComputeNew(randomTree);
//	        	try
//	        	{	        		
//	        		treelib[i]=randomTree;
//	        		for(j=0; j<NUMFITCASE;j++){
//	        			SemanticsTreelib[i][j]=semantic[j];
//	        		}	        		
//	        		
//	        	}
//	        	catch(Exception ex)
//	        	{
//	        		
//	        	}
//	        	//this.output.systemMessage("size: " + kdtreelib.size());
//	        }
//
//	 }
	
	
	byte TruncateTree(individual child,individual[] copychild, int[] mt,node[] nm)
		{
			int i;
			individual temp = new individual();
			int truncpoint, count;
			node node1=new node(), parentnode1=new node(), t=new node(),tchild1=new node(), tchild2=new node(), p=new node();
			count = 0;
			
			double[] a,b;
			double sum1, sum2;
			
			while(count < MAXATEMPT) {
				temp=new individual();
				temp.CopyIndividual(child, TRUE);
				truncpoint = IRandom(2, temp.size);
				temp.chrom.LocateNode(truncpoint, temp.chrom, null);
				
				node1=temp.chrom._idnode;
				parentnode1=temp.chrom._idnodeparent;
				
					
			
				t=new node("mul",VOIDVALUE);
				
				i = IRandom(0, gltcard - 1);
				tchild1 = new node(glterminal[i].name, VOIDVALUE);
				tchild2=new node("C", 1);
				
				
				a=ComputeNew(tchild1);
				b=ComputeNew(node1);
				
				sum1=0; sum2=0;
				
				for(i = 0; i < NUMFITCASE; i++) {
					sum1+=a[i]*a[i];
					sum2+=a[i]*b[i];
				}
				
				if(sum1==0){
					tchild2.value=1;
				}
				else{
					tchild2.value=sum2/sum1;
				}
				
				tchild1.sibling=tchild2;
				t.children=new node();
				t.children=tchild1;
				
				nm[0]=t;
			
				p = parentnode1.children;
				if(p == node1) // first child
					parentnode1.children = t;
				else {
					while(p.sibling != node1)
						p = p.sibling;
					p.sibling = t;
				}
				t.sibling = node1.sibling;
//				node1.DeleteNode(node1);
//				temp.DeleteChrom(node1);
				node1=null;
				temp.height = temp.chrom.GetHeightNode();
				temp.size=temp.chrom.GetSizeNode(temp.chrom, TRUE);
				if(temp.size<child.size) {
//					child.chrom.DeleteNode(child.chrom);
					child.chrom=null;
					child.chrom=temp.chrom;
//					if(status==0)
//					{
//					//  System.out.print("\n------------------------mutation---\n");
//					//  child.chrom.DisplaySTree(temp.chrom);
//					}
					child.height=temp.height;
					child.size=child.chrom.GetSizeNode(child.chrom, TRUE);
					temp.branch=child.chrom.GetAVGNode(child.chrom);
					//copychild[0].CopyIndividual(child, TRUE);
					copychild[0]=child;
					mt[0]=truncpoint;
					return TRUE;
				}
//				temp.chrom.DeleteNode(temp.chrom);
				temp=null;
				count++;
			}
			return FALSE;
		}


    public ArrayList<Double>[] invertAll(node a, int k, ArrayList<Double>[] out)
    {
    	ArrayList<Double>[] semantics = new ArrayList[NUMFITCASE];;
    	
    	for(int i=0; i<NUMFITCASE; i++)	{
    		semantics[i]= new ArrayList<Double>();
    	}
    	
    	
    	if(k==0) 
    	{
    		if (a.name == "add")
    		{	double[] c2 = ComputeNew(a.children.sibling);
	        	for(int i=0; i<NUMFITCASE; i++)	{	
	        		if(out[i].isEmpty())
	    				continue;
	        		// if Out is STAR mean that allvalues
	        		if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
	        		semantics[i].add(o - c2[i]);// = targetSemantics[i] - semanticsChild2[i];	
	        		}
    			}
	        	return semantics;
    		}
    		if (a.name == "sub")
    		{	double[] c2 = ComputeNew(a.children.sibling);
    			for(int i=0; i<NUMFITCASE; i++)	{
    				if(out[i].isEmpty())
	    				continue;
    				if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
	        		semantics[i].add(o + c2[i]);}
    			}
    			return semantics;
    		}
    		if (a.name == "mul")
    		{   
    			double[] c2 = ComputeNew(a.children.sibling); 
    			for(int i=0; i<NUMFITCASE; i++)	{    				
    				if(out[i].isEmpty())
	    				continue;
    				if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){	
						if(c2[i] != 0)
							semantics[i].add(o/c2[i]);
						else 
							if(o==0)
								semantics[i].add(STAR);							
		        		}
    			}
    			return semantics;
    		}
    		if (a.name == "div")
    		{
        		double[] c2 = ComputeNew(a.children.sibling); 
        		for(int i=0; i<NUMFITCASE; i++)	{ 
        			if(out[i].isEmpty())
	    				continue;
        			if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
						if(Math.abs(c2[i]) != Double.MAX_VALUE)
							semantics[i].add(o * c2[i]);
						else
							if(o==0)
								semantics[i].add(STAR);							
	        		}
        		}
    			return semantics;
    		}
    		
    	}
    	else //k==1
    	{
    		if (a.name == "add")
    		{
        		double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{ 
        			if(out[i].isEmpty())
		    				continue;
        			if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
		        		for(double o:out[i]){
	        			semantics[i].add(o-c1[i]);
        			}
        		}
    			return semantics;
    		}
    		if (a.name == "sub")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{   
        			if(out[i].isEmpty())
	    				continue;
        			if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
	        			semantics[i].add(c1[i]-o);
	        		}
        		}
    			return semantics;
    		}
    		if (a.name == "mul")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{   
        			if(out[i].isEmpty())
	    				continue;
        			if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
						if(c1[i] != 0)
							semantics[i].add(o/c1[i]);
						else if (o==0) {
							semantics[i].add(STAR);
							
						}						
	        		}
        		}
    			return semantics;
    		}
    		if (a.name == "div")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{  
        			if(out[i].isEmpty())
	    				continue;
        			if(out[i].contains(STAR))
	        		{
	        			semantics[i].add(STAR);
	        		}
	        		else
	        		for(double o:out[i]){
					if(o != 0)
						semantics[i].add(c1[i]/o);
					else if(c1[i]==0)
						semantics[i].add(STAR);
					 }
        		}
    			return semantics;
    		}
    		
    	}
    	// exp
    	if (a.name == "ep")
		{   for(int i=0; i<NUMFITCASE; i++)	{ 
			if(out[i].isEmpty())
				continue;
			if(out[i].contains(STAR))
    		{
    			semantics[i].add(STAR);
    		}
    		else
    		for(double o:out[i]){
				if(o >= 0)
					semantics[i].add(Math.log(o));				
	    		}
			}
			return semantics;
		}
    	
    	if (a.name == "log")
		{	
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].isEmpty())
    				continue;
    			if(out[i].contains(STAR))
        		{
        			semantics[i].add(STAR);
        		}
        		else
        		for(double o:out[i]){
	    			semantics[i].add(-Math.exp(o));
	    			semantics[i].add(Math.exp(o));
    			}
			}
			return semantics;
		}

    	if (a.name == "sin")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].isEmpty())
    				continue;
    			if(out[i].contains(STAR))
        		{
        			semantics[i].add(STAR);
        		}
        		else
		        	for(double o:out[i]){
			    		if (Math.abs(o) <= 1)	    		{
			    			semantics[i].add(Math.asin(o) - 2 * Math.PI);
			    			semantics[i].add(Math.asin(o));
			    		}
			    		
		        }
    		}
			return semantics;
		}
    	
    	if (a.name == "cos")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].isEmpty())
    				continue;
    			if(out[i].contains(STAR))
        		{
        			semantics[i].add(STAR);
        		}
        		else
        		for(double o:out[i]){
		    		if (Math.abs(o) <= 1)
		    		{
		    			semantics[i].add(Math.acos(o) - 2 * Math.PI);
		    			semantics[i].add(Math.acos(o));
		    		}
		    		
        		}
    		}
			return semantics;
		}

    	if (a.name == "sqrt")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].isEmpty())
    				continue;
    			if(out[i].contains(STAR))
        		{
        			semantics[i].add(STAR);
        		}
        		else
	        		for(double o:out[i]){
					if (o >= 0)
						semantics[i].add(o * o);
					
        		}
    		}
			return semantics;
		}

    	if (a.name == "sqr")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].isEmpty())
    				continue;
    			if(out[i].contains(STAR))
        		{
        			semantics[i].add(STAR);
        		}
        		else
	        		for(double o:out[i]){
		    		if (o>= 0)
		    		{
		    			semantics[i].add(Math.sqrt(o));
		    			semantics[i].add(-Math.sqrt(o));
		    		}
		    		
        		}
			}
			return semantics;
		}
    	
    	//
    	
		return semantics;
    	
    }

    public ArrayList<Double>[] invertAll2(node a, int k, ArrayList<Double>[] out)
    {
    	ArrayList<Double>[] semantics = new ArrayList[NUMFITCASE];;
    	
    	for(int i=0; i<NUMFITCASE; i++)	{
    		semantics[i]= new ArrayList<Double>();
    	}
    	
    	
    	if(k==0) 
    	{
    		if (a.name == "add")
    		{	double[] c2 = ComputeNew(a.children.sibling);
	        	for(int i=0; i<NUMFITCASE; i++)	{	
	        		if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        		semantics[i].add(o - c2[i]);// = targetSemantics[i] - semanticsChild2[i];	
	        		}
    			}
	        	return semantics;
    		}
    		if (a.name == "sub")
    		{	double[] c2 = ComputeNew(a.children.sibling);
    			for(int i=0; i<NUMFITCASE; i++)	{
    				if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        		semantics[i].add(o + c2[i]);}
    			}
    			return semantics;
    		}
    		if (a.name == "mul")
    		{   
    			double[] c2 = ComputeNew(a.children.sibling); 
    			for(int i=0; i<NUMFITCASE; i++)	{    				
    				if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){	
						if(c2[i] != 0)
							semantics[i].add(o/c2[i]);
						else 
							if(o==0)
								semantics[i].add(STAR);
							else				
								semantics[i].clear();
		        		}
    			}
    			return semantics;
    		}
    		if (a.name == "div")
    		{
        		double[] c2 = ComputeNew(a.children.sibling); 
        		for(int i=0; i<NUMFITCASE; i++)	{ 
        			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
						if(Math.abs(c2[i]) != Double.MAX_VALUE)
							semantics[i].add(o * c2[i]);
						else
							if(o==0)
								semantics[i].add(STAR);
							else
		//						semantics.add(null);
								semantics[i].clear(); // be empty
	        		}
        		}
    			return semantics;
    		}
    		
    	}
    	else //k==1
    	{
    		if (a.name == "add")
    		{
        		double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{ 
	        			if(out[i].contains(null))
		    				continue;
		        		for(double o:out[i]){
	        			semantics[i].add(o-c1[i]);
        			}
        		}
    			return semantics;
    		}
    		if (a.name == "sub")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{   
        			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
	        			semantics[i].add(c1[i]-o);
	        		}
        		}
    			return semantics;
    		}
    		if (a.name == "mul")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{   
        			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
						if(c1[i] != 0)
							semantics[i].add(o/c1[i]);
						else if (o==0) {
							semantics[i].add(STAR);
							
						}
						else {
							
							semantics[i].clear();
						}
	        		}
        		}
    			return semantics;
    		}
    		if (a.name == "div")
    		{
    			double[] c1 = ComputeNew(a.children); 
        		for(int i=0; i<NUMFITCASE; i++)	{  
        			if(out[i].contains(null))
	    				continue;
	        		for(double o:out[i]){
					if(o != 0)
						semantics[i].add(c1[i]/o);
					else if(c1[i]==0)
						semantics[i].add(STAR);
					else
						semantics[i].clear(); }
        		}
    			return semantics;
    		}
    		
    	}
    	// exp
    	if (a.name == "ep")
		{   for(int i=0; i<NUMFITCASE; i++)	{ 
			if(out[i].contains(null))
				continue;
    		for(double o:out[i]){
				if(o >= 0)
					semantics[i].add(Math.log(o));
				else
					semantics[i].clear();
	    		}
			}
			return semantics;
		}
    	
    	if (a.name == "log")
		{	
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].contains(null))
    				continue;
        		for(double o:out[i]){
	    			semantics[i].add(-Math.exp(o));
	    			semantics[i].add(Math.exp(o));
    			}
			}
			return semantics;
		}

    	if (a.name == "sin")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].contains(null))
    				continue;
		        		for(double o:out[i]){
			    		if (Math.abs(o) <= 1)	    		{
			    			semantics[i].add(Math.asin(o) - 2 * Math.PI);
			    			semantics[i].add(Math.asin(o));
			    		}
			    		else{
			    			semantics[i].clear();
			    			}
		        }
    		}
			return semantics;
		}
    	
    	if (a.name == "cos")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].contains(null))
    				continue;
        		for(double o:out[i]){
		    		if (Math.abs(o) <= 1)
		    		{
		    			semantics[i].add(Math.acos(o) - 2 * Math.PI);
		    			semantics[i].add(Math.acos(o));
		    		}
		    		else{
		    			semantics[i].clear();
		    		}
        		}
    		}
			return semantics;
		}

    	if (a.name == "sqrt")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].contains(null))
    				continue;
	        		for(double o:out[i]){
					if (o >= 0)
						semantics[i].add(o * o);
					else
						semantics[i].clear();
        		}
    		}
			return semantics;
		}

    	if (a.name == "sqr")
		{
    		for(int i=0; i<NUMFITCASE; i++)	{ 
    			if(out[i].contains(null))
    				continue;
	        		for(double o:out[i]){
		    		if (o>= 0)
		    		{
		    			semantics[i].add(Math.sqrt(o));
		    			semantics[i].add(-Math.sqrt(o));
		    		}
		    		else {
						semantics[i].clear();
					}
        		}
			}
			return semantics;
		}
    	
    	//
    	
		return semantics;
    	
    }

	byte TruncateTreeGrow1(individual child,individual[] copychild, int[] mt,node[] nm)
	{
		int i;
		individual temp = new individual();
		int truncpoint, count, thetree;
		node node1=new node(), parentnode1=new node(), t=new node(),tchild1=new node(), tchild2=new node(), p=new node();
		count = 0;
		
		double[] b,a;
		double sum1, sum2;
		
//		long starttime = System.currentTimeMillis();
		
		while(count < MAXATEMPT) {
			temp=new individual();
			temp.CopyIndividual(child, TRUE);
			truncpoint = IRandom(2, temp.size);
			
			thetree=IRandom(0, TREELIB_MAXDEPTH);	
			
			temp.chrom.LocateNode(truncpoint, temp.chrom, null);
			
			node1=temp.chrom._idnode;
			parentnode1=temp.chrom._idnodeparent;
			
				
		
			t=new node("mul",VOIDVALUE);		
			
		//	tchild1 = treelib[thetree];		
			tchild1 = GrowthTreeGen(thetree);
			tchild2=new node("C", 1);	
			
			g_numberTrunc++;
			
			a=ComputeNew(tchild1);
//			b=ComputeNew(node1);
			long startParttime = System.currentTimeMillis();	
			
			b=new double[NUMFITCASE];
//			System.out.println("Tr][cs:");
//			for(i=0;i<NUMFITCASE;i++)
//			{
//				System.out.print(" "+b[i]);
//			}
			ArrayList<Double>[] desiredSemantics = semanticBackPropagationAll(targetSemantics, temp.chrom, node1);
			if (desiredSemantics == null)
			{   
				count++;
				continue;
			}
	            	
			for(i = 0; i < NUMFITCASE; i++) {
				 if(!desiredSemantics[i].isEmpty()&&!desiredSemantics[i].contains(STAR)){	
//					    System.out.println(desiredSemantics[i]);
						for(double di:desiredSemantics[i]){
//							System.out.println(di);
//	         				 if(di!=Double.NaN)  {
//	         					b[i]=di;	         				 
//	         				 }	
							b[i]=di;
					 }
				 }	
			}
//			System.out.println("Sau:");
//			for(i=0;i<NUMFITCASE;i++)
//			{
//				System.out.print(" "+b[i]);
//			}
			startParttime = System.currentTimeMillis() - startParttime;	
			
			sum1=0; sum2=0;
			
			for(i = 0; i < NUMFITCASE; i++) {
				
				sum1+=a[i]*a[i];
				sum2+=a[i]*b[i];
			}
			
			if(sum1==0){
				tchild2.value=1;
			}
			else{
				tchild2.value=sum2/sum1;
			}
			
			tchild1.sibling=tchild2;
			t.children=new node();
			t.children=tchild1;
			
			nm[0]=t;
		
			p = parentnode1.children;
			if(p == node1) // first child
				parentnode1.children = t;
			else {
				while(p.sibling != node1)
					p = p.sibling;
				p.sibling = t;
			}
			t.sibling = node1.sibling;
//			node1.DeleteNode(node1);
//			temp.DeleteChrom(node1);
			node1=null;
			temp.height = temp.chrom.GetHeightNode();
			temp.size=temp.chrom.GetSizeNode(temp.chrom, TRUE);
			if(temp.size<child.size) {
//				child.chrom.DeleteNode(child.chrom);
				child.chrom=null;
				child.chrom=temp.chrom;
//				if(status==0)
//				{
//				//  System.out.print("\n------------------------mutation---\n");
//				//  child.chrom.DisplaySTree(temp.chrom);
//				}
				child.height=temp.height;
				child.size=child.chrom.GetSizeNode(child.chrom, TRUE);
				temp.branch=child.chrom.GetAVGNode(child.chrom);
				//copychild[0].CopyIndividual(child, TRUE);
				copychild[0]=child;
				mt[0]=truncpoint;
				
				// New 
//				starttime = System.currentTimeMillis() - starttime;			
				
//				g_TimeTrunc=g_TimeTrunc+starttime;					
				g_TimePartTrunc=g_TimePartTrunc+startParttime;
				
				return TRUE;
			}
//			temp.chrom.DeleteNode(temp.chrom);
			temp=null;
			count++;
		}
		return FALSE;
	}

	byte TruncateTreeGrow(individual child,individual[] copychild, int[] mt,node[] nm)
	{
		int i;
		individual temp = new individual();
		int truncpoint, count, thetree;
		node node1=new node(), parentnode1=new node(), t=new node(),tchild1=new node(), tchild2=new node(), p=new node();
		count = 0;
		
		double[] b,a;
		double sum1, sum2;
		
//		long starttime = System.currentTimeMillis();
		
		while(count < MAXATRIAL	) {
			
			g_numberTrunc++;
			
			temp=new individual();
			temp.CopyIndividual(child, TRUE);
			truncpoint = IRandom(2, temp.size);
			
			thetree=IRandom(0, TREELIB_MAXDEPTH);	
			
			temp.chrom.LocateNode(truncpoint, temp.chrom, null);
			
			node1=temp.chrom._idnode;
			parentnode1=temp.chrom._idnodeparent;
			
				
		
			t=new node("mul",VOIDVALUE);		
			
		//	tchild1 = treelib[thetree];		
			tchild1 = GrowthTreeGen(thetree);
			
			if((tchild1.GetSizeNode(tchild1, TRUE)+2) >= node1.GetSizeNode(node1,TRUE))
			{
				count++;				
				continue;
				
			}
			
			tchild2=new node("C", 1);	
			
			
			
			a=ComputeNew(tchild1);
//			b=ComputeNew(node1);
			long startParttime = System.currentTimeMillis();	
			
			b=new double[NUMFITCASE];
//			System.out.println("Tr][cs:");
//			for(i=0;i<NUMFITCASE;i++)
//			{
//				System.out.print(" "+b[i]);
//			}
			ArrayList<Double>[] desiredSemantics = semanticBackPropagationAll(targetSemantics, temp.chrom, node1);
			if (desiredSemantics == null)
			{   
				
				count++;
				continue;
			}
	            	
			for(i = 0; i < NUMFITCASE; i++) {
				 if(!desiredSemantics[i].isEmpty()&&!desiredSemantics[i].contains(STAR)){	
//					    System.out.println(desiredSemantics[i]);
						for(double di:desiredSemantics[i]){
//							System.out.println(di);
//	         				 if(di!=Double.NaN)  {
//	         					b[i]=di;	         				 
//	         				 }	
							b[i]=di;
					 }
				 }	
			}
//			System.out.println("Sau:");
//			for(i=0;i<NUMFITCASE;i++)
//			{
//				System.out.print(" "+b[i]);
//			}
			startParttime = System.currentTimeMillis() - startParttime;	
			
			sum1=0; sum2=0;
			
			for(i = 0; i < NUMFITCASE; i++) {
				
				sum1+=a[i]*a[i];
				sum2+=a[i]*b[i];
			}
			
			if(sum1==0){
				tchild2.value=1;
			}
			else{
				tchild2.value=sum2/sum1;
			}
			
			tchild1.sibling=tchild2;
			t.children=new node();
			t.children=tchild1;
			
			nm[0]=t;
		
			p = parentnode1.children;
			if(p == node1) // first child
				parentnode1.children = t;
			else {
				while(p.sibling != node1)
					p = p.sibling;
				p.sibling = t;
			}
			t.sibling = node1.sibling;
//			node1.DeleteNode(node1);
//			temp.DeleteChrom(node1);
			node1=null;
			temp.height = temp.chrom.GetHeightNode();
			temp.size=temp.chrom.GetSizeNode(temp.chrom, TRUE);
			if(temp.size<child.size) {
//				child.chrom.DeleteNode(child.chrom);
				child.chrom=null;
				child.chrom=temp.chrom;
//				if(status==0)
//				{
//				//  System.out.print("\n------------------------mutation---\n");
//				//  child.chrom.DisplaySTree(temp.chrom);
//				}
				child.height=temp.height;
				child.size=child.chrom.GetSizeNode(child.chrom, TRUE);
				temp.branch=child.chrom.GetAVGNode(child.chrom);
				//copychild[0].CopyIndividual(child, TRUE);
				copychild[0]=child;
				mt[0]=truncpoint;
				
				// New 
//				starttime = System.currentTimeMillis() - starttime;			
				
//				g_TimeTrunc=g_TimeTrunc+starttime;					
				g_TimePartTrunc=g_TimePartTrunc+startParttime;
				
				return TRUE;
			}
			
			
//			temp.chrom.DeleteNode(temp.chrom);
			temp=null;			
			count++;
		}
		g_numTruncFalse=g_numTruncFalse+1;
		return FALSE;
	}




	//phuong thuc tinh fitness cho tat ca cac individual trong mot quan the
	void TruncPopulation(double percent){
			int i,j,t, numIndiviadual;
			node[] _nm;
		 	int[] Sort=new int[poplen];	 	
		 
		 	for(i=0; i<poplen; i++)	{
		 		Sort[i]=i;	 		
		 	}
		 		 
		 	// Sort giam dam
		 	for(i=0; i<poplen-1; i++)	{
		 		for(j=i+1; j<poplen; j++){	 		
		 			if(oldpop[Sort[i]].size<oldpop[Sort[j]].size){
		 				t=Sort[i];
		 				Sort[i]=Sort[j];
		 				Sort[j]=t;
		 			}
		 		}
		 		
		 	}
			
		 	numIndiviadual=(int)(poplen*percent);
		 	
//		 	System.out.println(numIndiviadual);
		 	
			for(i = 0; i < numIndiviadual; i++){
				individual[] m_individual=new individual[1];
				int[] _mt=new int[1];
				_nm=new node[1];
//				System.out.println("Trước+"+oldpop[i].size);
				if(this.TruncateTreeGrow(oldpop[Sort[i]], m_individual,_mt,_nm)==TRUE){	
					
				oldpop[Sort[i]]=new individual();
				oldpop[Sort[i]].CopyIndividual(m_individual[0], TRUE);			
				oldpop[Sort[i]].evaluated=FALSE;
//				System.out.println("Sau+"+oldpop[i].size);
				}
				
			}
		}

	//phuong thuc tinh fitness cho tat ca cac individual trong mot quan the
	void TruncPopulationAvgSize(double scale){
			int i,j;
			node[] _nm;
		 	double sumSize,sizeThreshold; 	
		 	
//		 	double fitnessBefore;
//		 	double fitnessAfter;
		 	
//			double TheBestPrune, TheBestNotPrune;
//		 	double sumNotPrune, sumBeforePrune, sumAfterPrune;
		 	
//		 	long starttime = System.currentTimeMillis();
		 	
		 	g_numberTrunc=0;		 
		 	g_TimePartTrunc=0.0;
		 	g_numTruncFalse=0;
		 	
		 	 		 
		 	sumSize = oldpop[0].size;
			
			for(i = 1; i < poplen; i++) {				
				sumSize += oldpop[i].size;//				
			}
			
//			System.out.println("Tong:"+sumSize);
			sumSize=sumSize /poplen;
//			System.out.println("Avg:"+sumSize);
			sizeThreshold=sumSize+scale*sumSize;
//			System.out.println("Nguong:"+sizeThreshold);
		 	
//		 	System.out.println(numIndiviadual);
//			TheBestPrune=HUGE_VAL;
//		 	TheBestNotPrune=HUGE_VAL;
//		 	sumNotPrune=0.0;
//		 	sumBeforePrune=0.0;
//		 	sumAfterPrune=0.0;
		 	
			for(i = 0; i < poplen; i++){
				
				if(oldpop[i].size>sizeThreshold){
//					System.out.println("Vi tri:"+i);
					individual[] m_individual=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					
					//=====
//					if(oldpop[i].evaluated==FALSE){
//						fitnessBefore=ComputeRF(oldpop[i]);
//					}
//					else
//					{
//						fitnessBefore=oldpop[i].oldfitness;
//					}
					//====
					
	//				System.out.println("Trước+"+oldpop[i].size);
					if(this.TruncateTreeGrow(oldpop[i], m_individual,_mt,_nm)==TRUE){	
						
						oldpop[i]=new individual();
						oldpop[i].CopyIndividual(m_individual[0], TRUE);			
						oldpop[i].evaluated=FALSE;
		//				System.out.println("Sau+"+oldpop[i].size);
//						fitnessAfter=ComputeRF(oldpop[i]);
//						oldpop[i].oldfitness=fitnessAfter;
//						oldpop[i].fitness=fitnessAfter;
//						oldpop[i].evaluated=TRUE;
						
						g_numTrunc++;
						//=====================
//						sumBeforePrune=sumBeforePrune+fitnessBefore;
//						sumAfterPrune=sumAfterPrune+fitnessAfter;					
//						if(TheBestPrune>fitnessAfter){
//							TheBestPrune=fitnessAfter;
//						}
						//================================
						
//						DecreaseAllFitness=DecreaseAllFitness+(fitnessBefore-fitnessAfter);
//						if(fitnessAfter<=fitnessBefore){
//							g_numOfBetter++;						
//							DecreaseFitness=DecreaseFitness+(fitnessBefore-fitnessAfter);
//						}					
					}
//					else{
//						sumNotPrune=sumNotPrune+fitnessBefore;
//						if(TheBestNotPrune>fitnessBefore){
//							TheBestNotPrune=fitnessBefore;
//						}
//						
//					}
					
				}
//				else
//				{
//					if(oldpop[i].evaluated==FALSE){
//						fitnessBefore=ComputeRF(oldpop[i]);
//					}
//					else
//					{
//						fitnessBefore=oldpop[i].oldfitness;
//					}
//					
//					sumNotPrune=sumNotPrune+fitnessBefore;
//					if(TheBestNotPrune>fitnessBefore){
//						TheBestNotPrune=fitnessBefore;
//					}		
//				}
				
			}
			
			//new
				
			
			
			numberTrunc[gen]=g_numberTrunc;			
			TimePartTrunc[gen]=g_TimePartTrunc;
			
			
			
//			// Gan cac gia tri 
//			avgFitnessNotPrune[gen]=sumNotPrune/(1.0*(poplen-g_numTrunc));
//						
//			avgFitnessBeforePrune[gen]=sumBeforePrune/(1.0*g_numTrunc);			
//			avgFitnessAfterPrune[gen]=sumAfterPrune/(1.0*g_numTrunc);
//						
//			TheBestFitnessNotPrune[gen]=TheBestNotPrune;
//			TheBestFitnessPrune[gen]=TheBestPrune;
//				
//			if(TheBestNotPrune<TheBestPrune){
//				TheBestInNotPrune[gen]=1;
//			}
//			else{
//				TheBestInNotPrune[gen]=0;
//			}
//									
//			avgFitnessAllPopBefore[gen]=(sumNotPrune+sumBeforePrune)/(1.0*poplen);			
//			avgFitnessAllPopAfter[gen]=(sumNotPrune+sumAfterPrune)/(1.0*poplen);
//						
//						
//						if(sumBeforePrune<sumAfterPrune)
//						{
//							TheBestAvgInAllPopBefore[gen]=1;
//						}
//						else
//						{
//							TheBestAvgInAllPopBefore[gen]=0;
//						}
//						
//						
//						if(avgFitnessNotPrune[gen]<avgFitnessAfterPrune[gen])
//						{
//							TheBestAvgInNotPrune[gen]=1;
//						}
//						else
//						{
//							TheBestAvgInNotPrune[gen]=0;
//						}
//								
						
						//===========================
		}

	//phuong thuc tinh fitness cho tat ca cac individual trong mot quan the
	void TruncPopulationAvgSizeGen(double scale){
			int i;
			node[] _nm;
		 	double sumSize,sizeThreshold; 	
		 	 		 
		 	sumSize = oldpop[0].size;
			
			for(i = 1; i < poplen; i++) {				
				sumSize += oldpop[i].size;//				
			}
			
//			System.out.println("Tong:"+sumSize);
			sumSize=sumSize /poplen;
//			System.out.println("Avg:"+sumSize);
			
			sizeThreshold=sumSize+(1.0-(double)gen/NUMGEN)*sumSize*scale;
			
			
			System.out.println(((1.0-(double)gen/NUMGEN)*sumSize*scale) +"-"+((1.0-gen/NUMGEN)*sumSize*scale));
		 	
//		 	System.out.println(numIndiviadual);
		 	
			for(i = 0; i < poplen; i++){
				if(oldpop[i].size>sizeThreshold){
//					System.out.println("Vi tri:"+i);
					individual[] m_individual=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
	//				System.out.println("Trước+"+oldpop[i].size);
					if(this.TruncateTreeGrow(oldpop[i], m_individual,_mt,_nm)==TRUE){	
						
					oldpop[i]=new individual();
					oldpop[i].CopyIndividual(m_individual[0], TRUE);			
					oldpop[i].evaluated=FALSE;
	//				System.out.println("Sau+"+oldpop[i].size);
					}
				}
				
			}
		}

	
	void EvolutionWithElitismTruncAvgSize(int numGentrunc, double scale){
		int i, j, l, k;
		//individual[] temp;
		byte _flippc=0,_flippm=0;
//		String _temp1="", _temp2="";
		int[] _cp;
		node[] _nd,_nm;
		// Khoi tao quan the dau tien 
		RampedInit(6, 0.5);
		//RampedInitOffLine(6, 0.5,15);
		
		int genFirst;
		genFirst=generation-numGentrunc;
		
		gen = 0;
	
//		constructiveRate = new double[NUMGEN];
//		semanticDistance = new double[NUMGEN];
		
		
		while((gen < genFirst) && SuccPredicate == FALSE) {
			
				g_TimeCaculateFitness=0.0;
				g_TimeTrunc=0.0;
			
				long starttime = System.currentTimeMillis();
				ComputeFitness();		
				starttime = System.currentTimeMillis() - starttime;
				g_TimeCaculateFitness=g_TimeCaculateFitness+starttime;
				
//				int g_ncross = 0;
//				int g_numOfBetter = 0;
//				double g_sd = 0;
				
//				PrintToFile();
				// copy anh tot nhat sang the he sau	 	
				newpop[0].CopyIndividual(bestcurrent[gen], TRUE);
				
				// dot bien anh thu 2
			    l=1;
			    i = TourSelect(TOURSIZE);
			    
			    newpop[l].CopyIndividual(oldpop[i], TRUE);
				individual[] m_individual0=new individual[1];
				int[] _mt0=new int[1];
				_nm=new node[1];
				this.ReplaceSubTree(newpop[l], m_individual0, 15, TRUE,_mt0,_nm);
				newpop[l]=new individual();
				newpop[l].CopyIndividual(m_individual0[0], TRUE);			
				newpop[l].evaluated=FALSE;		
				
				m_individual0=null;
				  
				   
	//----------------------
//				FileInputStream instream = null;
//				PrintStream outstream = null;
//				PrintStream console = System.out;
//				try {
//					outstream = new PrintStream(new FileOutputStream("c:/result/" + "detail_"+ gen + ".txt"));
//					System.setOut(outstream);
//				} catch(Exception e) {
//					System.err.println("Error Occurred.");
//				}
//				for(i = 0; i < poplen; i++) {
//					System.out.printf("%3.5f  ",oldpop[i].fitness);
//					if((i+1) % 10 == 0) {
//						System.out.println();
//					}
//				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(oldpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
	//------------------------		
				l=2;			
				while(l < poplen) {
				//	System.out.println("Generation "+ String.valueOf(gen)+":");			
			
					
					i = TourSelect(TOURSIZE);
					j = TourSelect(TOURSIZE);
//				    System.out.printf("i:%d-j:%d", i,j);
//				    System.out.println();
//				    _temp1="";
//					_temp1=oldpop[i].chrom.TreeToStringN(oldpop[i].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp2="";
//					_temp2=oldpop[j].chrom.TreeToStringN(oldpop[j].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
//						
				    _flippc=Flip(pcross);
//				    System.out.printf("pcross:%d", _flippc);
//				    System.out.println();
					if(_flippc == 1) {					
						individual[] i_temp=new individual[2];
						_cp=new int[2];
						_nd=new node[2];
						if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)				
						{
							newpop[l].CopyIndividual(i_temp[0],TRUE);
							newpop[l+1].CopyIndividual(i_temp[1],TRUE);
							
							newpop[l].evaluated=FALSE;
							newpop[l+1].evaluated=FALSE;
//							System.out.printf("%s","crosspoint 1:"+_cp[0]+" crosspoint 2:"+_cp[1]);
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(_nd[0]);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(_nd[1]);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
							ncross++;
							i_temp=null;
							
							// semantic distance
//							if(newpop[l].isBetterThan(oldpop[i]))
//								g_numOfBetter += 1;
//							if(newpop[l].isBetterThan(oldpop[j]))
//								g_numOfBetter += 1;
//							if(newpop[l+1].isBetterThan(oldpop[i]))
//								g_numOfBetter += 1;
//							if(newpop[l+1].isBetterThan(oldpop[j]))
//								g_numOfBetter += 1;
//							// semantic distance
//							g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//							g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);					
//							g_ncross++;
							
						}
						else
						{						
							newpop[l].CopyIndividual(oldpop[i], TRUE);
							newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//							System.out.printf("%s","reproduction");
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
							i_temp=null;
						} 
					} 
					else
					{
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//						System.out.printf("%s","reproduction");
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();					
					}
					// mutation test
					_flippm=Flip(pmutate);
//					System.out.printf("pmutation:%d", _flippm);
//				    System.out.println();
					if(_flippm == 1) {					
						individual[] m_individual=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm);
						newpop[l]=new individual();
						newpop[l].CopyIndividual(m_individual[0], TRUE);
						
						newpop[l].evaluated=FALSE;
						
//						System.out.printf("%s","mutation "+l+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();					
						nmutate++;
						m_individual=null;
					}
					else
					{
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
					}
					
					if(Flip(pmutate) == 1) {
						individual[] m_individual1=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm);
						newpop[l+1]=new individual();
						newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
						
						newpop[l+1].evaluated=FALSE;
//						System.out.printf("%s","mutation "+(l+1)+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						nmutate++;
						m_individual1=null;
					}
					else
					{
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
					}
					l += 2;
				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(newpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
//				outstream.close();
//				System.setOut(console);
				
//				constructiveRate[gen] = g_numOfBetter / (4.0 * g_ncross);
//				semanticDistance[gen] = g_sd / (2.0 * g_ncross);
				TimeCaculateFitness[gen]=g_TimeCaculateFitness;
				gen++;
				for(k = 0; k < poplen; k++)
				{
					oldpop[k]=null;
					oldpop[k]=new individual();
					oldpop[k].CopyIndividual(newpop[k], TRUE);
				}
			}			
		
		
		
		while((gen < generation) && SuccPredicate == FALSE) {
//			ComputeFitnessBefore();
			g_numOfBetter=0;
			g_numTrunc=0;
			g_numTruncFalse=0;
			
//			DecreaseFitness=0.0;
//			DecreaseAllFitness=0.0;
			g_TimeCaculateFitness=0.0;
			g_TimeTrunc=0.0;
			
			long starttimeTruc = System.currentTimeMillis();
			
			TruncPopulationAvgSize(scale);
			
			starttimeTruc = System.currentTimeMillis() - starttimeTruc;
			g_TimeTrunc=g_TimeTrunc+starttimeTruc;
			
//			bestBeforeFitness[gen] = g_numOfBetter / (1.0 * g_numTrunc);
//			avgDecreaseFitness[gen] = DecreaseFitness / (1.0 * g_numTrunc);
//			avgDecreaseAllFitness[gen] = DecreaseAllFitness / (1.0 * g_numTrunc);
			
			numTruncSuccess[gen] = g_numTrunc;
			numTruncFalse[gen] = g_numTruncFalse;
			
			long starttime = System.currentTimeMillis();
			
			ComputeFitness();	
			
			starttime = System.currentTimeMillis() - starttime;
			g_TimeCaculateFitness=g_TimeCaculateFitness+starttime;
			
			
//			int g_ncross = 0;
//			int g_numOfBetter = 0;
//			double g_sd = 0;
			
//			PrintToFile();
			// copy anh tot nhat sang the he sau	 	
			newpop[0].CopyIndividual(bestcurrent[gen], TRUE);
			
			// dot bien anh thu 2
		    l=1;
		    i = TourSelect(TOURSIZE);
		    
		    newpop[l].CopyIndividual(oldpop[i], TRUE);
			individual[] m_individual0=new individual[1];
			int[] _mt0=new int[1];
			_nm=new node[1];
			this.ReplaceSubTree(newpop[l], m_individual0, 15, TRUE,_mt0,_nm);
			newpop[l]=new individual();
			newpop[l].CopyIndividual(m_individual0[0], TRUE);			
			newpop[l].evaluated=FALSE;		
			
			m_individual0=null;
			  
			   
//----------------------
//			FileInputStream instream = null;
//			PrintStream outstream = null;
//			PrintStream console = System.out;
//			try {
//				outstream = new PrintStream(new FileOutputStream("c:/result/" + "detail_"+ gen + ".txt"));
//				System.setOut(outstream);
//			} catch(Exception e) {
//				System.err.println("Error Occurred.");
//			}
//			for(i = 0; i < poplen; i++) {
//				System.out.printf("%3.5f  ",oldpop[i].fitness);
//				if((i+1) % 10 == 0) {
//					System.out.println();
//				}
//			}
//			for(int ii = 0; ii < poplen; ii++) {			
//				_temp1="";
//				_temp1=oldpop[ii].chrom.TreeToStringN(oldpop[ii].chrom);
//				System.out.printf("%s",ii+" "+ _temp1);			
//				System.out.println();
//			}
//------------------------		
			l=2;			
			while(l < poplen) {
			//	System.out.println("Generation "+ String.valueOf(gen)+":");			
		
				
				i = TourSelect(TOURSIZE);
				j = TourSelect(TOURSIZE);
//			    System.out.printf("i:%d-j:%d", i,j);
//			    System.out.println();
//			    _temp1="";
//				_temp1=oldpop[i].chrom.TreeToStringN(oldpop[i].chrom);
//				System.out.printf("%s", _temp1);			
//				System.out.println();
//				_temp2="";
//				_temp2=oldpop[j].chrom.TreeToStringN(oldpop[j].chrom);
//				System.out.printf("%s", _temp2);			
//				System.out.println();
//					
			    _flippc=Flip(pcross);
//			    System.out.printf("pcross:%d", _flippc);
//			    System.out.println();
				if(_flippc == 1) {					
					individual[] i_temp=new individual[2];
					_cp=new int[2];
					_nd=new node[2];
					if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)				
					{
						newpop[l].CopyIndividual(i_temp[0],TRUE);
						newpop[l+1].CopyIndividual(i_temp[1],TRUE);
						
						newpop[l].evaluated=FALSE;
						newpop[l+1].evaluated=FALSE;
//						System.out.printf("%s","crosspoint 1:"+_cp[0]+" crosspoint 2:"+_cp[1]);
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(_nd[0]);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(_nd[1]);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						ncross++;
						i_temp=null;
						
						// semantic distance
//						if(newpop[l].isBetterThan(oldpop[i]))
//							g_numOfBetter += 1;
//						if(newpop[l].isBetterThan(oldpop[j]))
//							g_numOfBetter += 1;
//						if(newpop[l+1].isBetterThan(oldpop[i]))
//							g_numOfBetter += 1;
//						if(newpop[l+1].isBetterThan(oldpop[j]))
//							g_numOfBetter += 1;
						// semantic distance
//						g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//						g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);					
//						g_ncross++;
						
					}
					else
					{						
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//						System.out.printf("%s","reproduction");
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						i_temp=null;
					} 
				} 
				else
				{
					newpop[l].CopyIndividual(oldpop[i], TRUE);
					newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//					System.out.printf("%s","reproduction");
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();					
				}
				// mutation test
				_flippm=Flip(pmutate);
//				System.out.printf("pmutation:%d", _flippm);
//			    System.out.println();
				if(_flippm == 1) {					
					individual[] m_individual=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm);
					newpop[l]=new individual();
					newpop[l].CopyIndividual(m_individual[0], TRUE);
					
					newpop[l].evaluated=FALSE;
					
//					System.out.printf("%s","mutation "+l+" replacepoint:"+_mt[0]);
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(_nm[0]);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();					
					nmutate++;
					m_individual=null;
				}
				else
				{
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
				}
				
				if(Flip(pmutate) == 1) {
					individual[] m_individual1=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm);
					newpop[l+1]=new individual();
					newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
					
					newpop[l+1].evaluated=FALSE;
//					System.out.printf("%s","mutation "+(l+1)+" replacepoint:"+_mt[0]);
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(_nm[0]);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
					nmutate++;
					m_individual1=null;
				}
				else
				{
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
				}
				l += 2;
			}
//			for(int ii = 0; ii < poplen; ii++) {			
//				_temp1="";
//				_temp1=oldpop[ii].chrom.TreeToStringN(newpop[ii].chrom);
//				System.out.printf("%s",ii+" "+ _temp1);			
//				System.out.println();
//			}
//			outstream.close();
//			System.setOut(console);
//			constructiveRate[gen] = g_numOfBetter / (4.0 * g_ncross);
//			semanticDistance[gen] = g_sd / (2.0 * g_ncross);
			
			TimeCaculateFitness[gen]=g_TimeCaculateFitness;
			TimeTrunc[gen]=g_TimeTrunc;
			
			gen++;
			for(k = 0; k < poplen; k++)
			{
				oldpop[k]=null;
				oldpop[k]=new individual();
				oldpop[k].CopyIndividual(newpop[k], TRUE);
			}
		}
	}


	void EvolutionWithElitismTruncAvgSizeGen(int numGentrunc, double scale){
		int i, j, l, k;
		//individual[] temp;
		byte _flippc=0,_flippm=0;
//		String _temp1="", _temp2="";
		int[] _cp;
		node[] _nd,_nm;
		// Khoi tao quan the dau tien 
		RampedInit(6, 0.5);
		//RampedInitOffLine(6, 0.5,15);
		
		int genFirst;
		genFirst=generation-numGentrunc;
		
		gen = 0;
	
//		constructiveRate = new double[NUMGEN];
//		semanticDistance = new double[NUMGEN];
		
		while((gen < genFirst) && SuccPredicate == FALSE) {
				
				ComputeFitness();		
				
//				int g_ncross = 0;
//				int g_numOfBetter = 0;
//				double g_sd = 0;
				
//				PrintToFile();
				// copy anh tot nhat sang the he sau	 	
				newpop[0].CopyIndividual(bestcurrent[gen], TRUE);
				
				// dot bien anh thu 2
			    l=1;
			    i = TourSelect(TOURSIZE);
			    
			    newpop[l].CopyIndividual(oldpop[i], TRUE);
				individual[] m_individual0=new individual[1];
				int[] _mt0=new int[1];
				_nm=new node[1];
				this.ReplaceSubTree(newpop[l], m_individual0, 15, TRUE,_mt0,_nm);
				newpop[l]=new individual();
				newpop[l].CopyIndividual(m_individual0[0], TRUE);			
				newpop[l].evaluated=FALSE;		
				
				m_individual0=null;
				  
				   
	//----------------------
//				FileInputStream instream = null;
//				PrintStream outstream = null;
//				PrintStream console = System.out;
//				try {
//					outstream = new PrintStream(new FileOutputStream("c:/result/" + "detail_"+ gen + ".txt"));
//					System.setOut(outstream);
//				} catch(Exception e) {
//					System.err.println("Error Occurred.");
//				}
//				for(i = 0; i < poplen; i++) {
//					System.out.printf("%3.5f  ",oldpop[i].fitness);
//					if((i+1) % 10 == 0) {
//						System.out.println();
//					}
//				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(oldpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
	//------------------------		
				l=2;			
				while(l < poplen) {
				//	System.out.println("Generation "+ String.valueOf(gen)+":");			
			
					
					i = TourSelect(TOURSIZE);
					j = TourSelect(TOURSIZE);
//				    System.out.printf("i:%d-j:%d", i,j);
//				    System.out.println();
//				    _temp1="";
//					_temp1=oldpop[i].chrom.TreeToStringN(oldpop[i].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp2="";
//					_temp2=oldpop[j].chrom.TreeToStringN(oldpop[j].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
//						
				    _flippc=Flip(pcross);
//				    System.out.printf("pcross:%d", _flippc);
//				    System.out.println();
					if(_flippc == 1) {					
						individual[] i_temp=new individual[2];
						_cp=new int[2];
						_nd=new node[2];
						if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)				
						{
							newpop[l].CopyIndividual(i_temp[0],TRUE);
							newpop[l+1].CopyIndividual(i_temp[1],TRUE);
							
							newpop[l].evaluated=FALSE;
							newpop[l+1].evaluated=FALSE;
//							System.out.printf("%s","crosspoint 1:"+_cp[0]+" crosspoint 2:"+_cp[1]);
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(_nd[0]);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(_nd[1]);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
							ncross++;
							i_temp=null;
							
//							// semantic distance
//							if(newpop[l].isBetterThan(oldpop[i]))
//								g_numOfBetter += 1;
//							if(newpop[l].isBetterThan(oldpop[j]))
//								g_numOfBetter += 1;
//							if(newpop[l+1].isBetterThan(oldpop[i]))
//								g_numOfBetter += 1;
//							if(newpop[l+1].isBetterThan(oldpop[j]))
//								g_numOfBetter += 1;
//							// semantic distance
//							g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//							g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);					
//							g_ncross++;
							
						}
						else
						{						
							newpop[l].CopyIndividual(oldpop[i], TRUE);
							newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//							System.out.printf("%s","reproduction");
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
							i_temp=null;
						} 
					} 
					else
					{
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//						System.out.printf("%s","reproduction");
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();					
					}
					// mutation test
					_flippm=Flip(pmutate);
//					System.out.printf("pmutation:%d", _flippm);
//				    System.out.println();
					if(_flippm == 1) {					
						individual[] m_individual=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm);
						newpop[l]=new individual();
						newpop[l].CopyIndividual(m_individual[0], TRUE);
						
						newpop[l].evaluated=FALSE;
						
//						System.out.printf("%s","mutation "+l+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();					
						nmutate++;
						m_individual=null;
					}
					else
					{
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
					}
					
					if(Flip(pmutate) == 1) {
						individual[] m_individual1=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm);
						newpop[l+1]=new individual();
						newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
						
						newpop[l+1].evaluated=FALSE;
//						System.out.printf("%s","mutation "+(l+1)+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						nmutate++;
						m_individual1=null;
					}
					else
					{
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
					}
					l += 2;
				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(newpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
//				outstream.close();
//				System.setOut(console);
				
//				constructiveRate[gen] = g_numOfBetter / (4.0 * g_ncross);
//				semanticDistance[gen] = g_sd / (2.0 * g_ncross);
				
				gen++;
				for(k = 0; k < poplen; k++)
				{
					oldpop[k]=null;
					oldpop[k]=new individual();
					oldpop[k].CopyIndividual(newpop[k], TRUE);
				}
			}			
		
		
		
		while((gen < generation) && SuccPredicate == FALSE) {
			ComputeFitnessBefore();
			TruncPopulationAvgSizeGen(scale);
			ComputeFitness();		
			
//			int g_ncross = 0;
//			int g_numOfBetter = 0;
//			double g_sd = 0;
			
//			PrintToFile();
			// copy anh tot nhat sang the he sau	 	
			newpop[0].CopyIndividual(bestcurrent[gen], TRUE);
			
			// dot bien anh thu 2
		    l=1;
		    i = TourSelect(TOURSIZE);
		    
		    newpop[l].CopyIndividual(oldpop[i], TRUE);
			individual[] m_individual0=new individual[1];
			int[] _mt0=new int[1];
			_nm=new node[1];
			this.ReplaceSubTree(newpop[l], m_individual0, 15, TRUE,_mt0,_nm);
			newpop[l]=new individual();
			newpop[l].CopyIndividual(m_individual0[0], TRUE);			
			newpop[l].evaluated=FALSE;		
			
			m_individual0=null;
			  
			   
//----------------------
//			FileInputStream instream = null;
//			PrintStream outstream = null;
//			PrintStream console = System.out;
//			try {
//				outstream = new PrintStream(new FileOutputStream("c:/result/" + "detail_"+ gen + ".txt"));
//				System.setOut(outstream);
//			} catch(Exception e) {
//				System.err.println("Error Occurred.");
//			}
//			for(i = 0; i < poplen; i++) {
//				System.out.printf("%3.5f  ",oldpop[i].fitness);
//				if((i+1) % 10 == 0) {
//					System.out.println();
//				}
//			}
//			for(int ii = 0; ii < poplen; ii++) {			
//				_temp1="";
//				_temp1=oldpop[ii].chrom.TreeToStringN(oldpop[ii].chrom);
//				System.out.printf("%s",ii+" "+ _temp1);			
//				System.out.println();
//			}
//------------------------		
			l=2;			
			while(l < poplen) {
			//	System.out.println("Generation "+ String.valueOf(gen)+":");			
		
				
				i = TourSelect(TOURSIZE);
				j = TourSelect(TOURSIZE);
//			    System.out.printf("i:%d-j:%d", i,j);
//			    System.out.println();
//			    _temp1="";
//				_temp1=oldpop[i].chrom.TreeToStringN(oldpop[i].chrom);
//				System.out.printf("%s", _temp1);			
//				System.out.println();
//				_temp2="";
//				_temp2=oldpop[j].chrom.TreeToStringN(oldpop[j].chrom);
//				System.out.printf("%s", _temp2);			
//				System.out.println();
//					
			    _flippc=Flip(pcross);
//			    System.out.printf("pcross:%d", _flippc);
//			    System.out.println();
				if(_flippc == 1) {					
					individual[] i_temp=new individual[2];
					_cp=new int[2];
					_nd=new node[2];
					if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)				
					{
						newpop[l].CopyIndividual(i_temp[0],TRUE);
						newpop[l+1].CopyIndividual(i_temp[1],TRUE);
						
						newpop[l].evaluated=FALSE;
						newpop[l+1].evaluated=FALSE;
//						System.out.printf("%s","crosspoint 1:"+_cp[0]+" crosspoint 2:"+_cp[1]);
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(_nd[0]);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(_nd[1]);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						ncross++;
						i_temp=null;
						
//						// semantic distance
//						if(newpop[l].isBetterThan(oldpop[i]))
//							g_numOfBetter += 1;
//						if(newpop[l].isBetterThan(oldpop[j]))
//							g_numOfBetter += 1;
//						if(newpop[l+1].isBetterThan(oldpop[i]))
//							g_numOfBetter += 1;
//						if(newpop[l+1].isBetterThan(oldpop[j]))
//							g_numOfBetter += 1;
//						// semantic distance
//						g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//						g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);					
//						g_ncross++;
						
					}
					else
					{						
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//						System.out.printf("%s","reproduction");
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						i_temp=null;
					} 
				} 
				else
				{
					newpop[l].CopyIndividual(oldpop[i], TRUE);
					newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//					System.out.printf("%s","reproduction");
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();					
				}
				// mutation test
				_flippm=Flip(pmutate);
//				System.out.printf("pmutation:%d", _flippm);
//			    System.out.println();
				if(_flippm == 1) {					
					individual[] m_individual=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm);
					newpop[l]=new individual();
					newpop[l].CopyIndividual(m_individual[0], TRUE);
					
					newpop[l].evaluated=FALSE;
					
//					System.out.printf("%s","mutation "+l+" replacepoint:"+_mt[0]);
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(_nm[0]);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();					
					nmutate++;
					m_individual=null;
				}
				else
				{
//					_temp1="";
//					_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//					System.out.printf("%s", _temp1);			
//					System.out.println();
				}
				
				if(Flip(pmutate) == 1) {
					individual[] m_individual1=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm);
					newpop[l+1]=new individual();
					newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
					
					newpop[l+1].evaluated=FALSE;
//					System.out.printf("%s","mutation "+(l+1)+" replacepoint:"+_mt[0]);
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(_nm[0]);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
					nmutate++;
					m_individual1=null;
				}
				else
				{
//					_temp2="";
//					_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//					System.out.printf("%s", _temp2);			
//					System.out.println();
				}
				l += 2;
			}
//			for(int ii = 0; ii < poplen; ii++) {			
//				_temp1="";
//				_temp1=oldpop[ii].chrom.TreeToStringN(newpop[ii].chrom);
//				System.out.printf("%s",ii+" "+ _temp1);			
//				System.out.println();
//			}
//			outstream.close();
//			System.setOut(console);
//			constructiveRate[gen] = g_numOfBetter / (4.0 * g_ncross);
//			semanticDistance[gen] = g_sd / (2.0 * g_ncross);
			
			
			gen++;
			for(k = 0; k < poplen; k++)
			{
				oldpop[k]=null;
				oldpop[k]=new individual();
				oldpop[k].CopyIndividual(newpop[k], TRUE);
			}
		}
	}

	// **************************************************
	// crossover
		byte SubTreeSwap(individual parent1, individual parent2, individual[] child,int[] cp,node[] nd)
		{
			individual temp1, temp2;// hai bien tam de luu hai parent		
			temp1 = new individual();
			temp2 = new individual();
			int count, crosspoint1, crosspoint2;
			node node1, node2, t;
			node1=new node();node2=new node();t=new node();
			double valuetemp;
			String nametemp;
			count = 0;
			//so lan thu mutation
			while(count < MAXATEMPT) {
				// copy the parents
				temp1.CopyIndividual(parent1, TRUE);
				temp2.CopyIndividual(parent2, TRUE);
				// Chon ngau nhien diem de lai ghep
				crosspoint1 = IRandom(2, temp1.size);
				crosspoint2 = IRandom(2, temp2.size);
				temp1.chrom.LocateNode(crosspoint1, temp1.chrom, null);
				
				node1=temp1.chrom._idnode; 
				
				temp2.chrom.LocateNode(crosspoint2, temp2.chrom, null);
				node2=temp2.chrom._idnode;
				
				nametemp = node1.name;
				node1.name = node2.name;
				node2.name = nametemp;
				valuetemp = node1.value;
				node1.value = node2.value;
				node2.value = valuetemp;
				
				// then swap the children
				t = node1.children;
				node1.children = node2.children;
				node2.children = t;
				// compute the heigh after that
				temp1.height = temp1.chrom.GetHeightNode();
				temp2.height = temp2.chrom.GetHeightNode();
				if((temp1.height <= MAXDEPTH) && (temp2.height <= MAXDEPTH)) {
					temp1.size = temp1.chrom.GetSizeNode(temp1.chrom, TRUE);
					temp1.branch=temp1.chrom.GetAVGNode(temp1.chrom);
					temp2.size=temp2.chrom.GetSizeNode(temp2.chrom, TRUE);
					temp2.branch=temp2.chrom.GetAVGNode(temp2.chrom);
					child[0]=temp1;
					child[1]=temp2;
					cp[0]=crosspoint1;
					cp[1]=crosspoint2;
					nd[0]=node1;
					nd[1]=node2;
					return TRUE;
				} else {
//					temp1.DeleteChrom();
//					temp1=null;				
//					temp2.chrom.DeleteChrom(temp1.chrom);
//					temp2.DeleteChrom();
//					temp2=null;
				}
				count++;
			}
			return FALSE;
		}

	//Mutation
	byte ReplaceSubTree(individual child,individual[] copychild, int maxdepth, byte type,int[] mt,node[] nm)
	{
		individual temp = new individual();
		int replacepoint, count;
		node node1=new node(), parentnode1=new node(), t=new node(), p=new node();
		count = 0;
		
		while(count < MAXATEMPT) {
			temp=new individual();
			temp.CopyIndividual(child, TRUE);
			replacepoint = IRandom(2, temp.size);
			temp.chrom.LocateNode(replacepoint, temp.chrom, null);
			
			node1=temp.chrom._idnode;
			parentnode1=temp.chrom._idnodeparent;
			
			if(type == TRUE) // growth
			  t = GrowthTreeGen(maxdepth);
			else t = FullTreeGen(maxdepth);
			nm[0]=t;
		
			p = parentnode1.children;
			if(p == node1) // first child
			parentnode1.children = t;
			else {
				while(p.sibling != node1)
					p = p.sibling;
				p.sibling = t;
			}
			t.sibling = node1.sibling;
//			node1.DeleteNode(node1);
//			temp.DeleteChrom(node1);
			node1=null;
			temp.height = temp.chrom.GetHeightNode();
			
			if(temp.height <= MAXDEPTH) {
//				child.chrom.DeleteNode(child.chrom);
				child.chrom=null;
				child.chrom=temp.chrom;
//				if(status==0)
//				{
//				//  System.out.print("\n------------------------mutation---\n");
//				//  child.chrom.DisplaySTree(temp.chrom);
//				}
				child.height=temp.height;
				child.size=child.chrom.GetSizeNode(child.chrom, TRUE);
				temp.branch=child.chrom.GetAVGNode(child.chrom);
				//copychild[0].CopyIndividual(child, TRUE);
				copychild[0]=child;
				mt[0]=replacepoint;
				return TRUE;
			}
//			temp.chrom.DeleteNode(temp.chrom);
			temp=null;
			count++;
		}
		return FALSE;
	}

// Read training data
	void SetFitCase(){
		String csvFile = DATADIR + PROBLEM +".training.in";;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = " ";
		
		int i,j;
		
		try {
			 
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			i=0;
			while (((line = br.readLine()) != null)&&(i<NUMFITCASE)) {
	 
			        // use comma as separator)
				String[] dataline = line.split(cvsSplitBy);
				fitcase[i]=new sample();
				for(j=0;j<NUMVAR;j++)
				{
					   fitcase[i].x[j]= Double.parseDouble(dataline[j]);
				}
				fitcase[i].y= Double.parseDouble(dataline[NUMVAR]);
				i=i+1;
	 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
void PrintFitCase()
{
	int i;
	for(i=0;i<NUMFITCASE;i++)
		System.out.print(fitcase[i].x+"|"+fitcase[i].y+"\n");
}
	// ********************************************************************
void SetFitTest(){
	String csvFile = DATADIR + PROBLEM +".testing.in";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = " ";
	
	int i,j;
	
	try {
		 
		br = new BufferedReader(new FileReader(csvFile));
		line = br.readLine();
		
		i=0;
		while (((line = br.readLine()) != null)&&(i<NUMFITTEST)) {
 
		        // use comma as separator)
			String[] dataline = line.split(cvsSplitBy);
			fittest[i]=new sample();
			for(j=0;j<NUMVAR;j++)
			{
				   fittest[i].x[j]= Double.parseDouble(dataline[j]);
			}
			fittest[i].y= Double.parseDouble(dataline[NUMVAR]);
			i=i+1;
 
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
double GenerateER(String name)
{
 if(name.equals("ERC"))
   return -1+2*Next_Double();
return VOIDVALUE;
}

//compute for a node
double[] ComputeNew(node st){
	node p;
	double[] l;
	double[] r;
	double[] result=new double[NUMFITCASE];
	
	for(int j=0;j<NUMVAR;j++){		
		
		if(st.name.equals("X"+String.valueOf(j+1))){
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]=fitcase[i].x[j];
			}
			return result;
		}
	}
	
	if(st.name.equals("C")){
		for(int i=0; i<NUMFITCASE; i++)
		{
			result[i]=st.value;
		}
		return result;
	}
	else
		if(st.name.equals("ERC")) {
			if(st.value==VOIDVALUE) //if it has not been initialized then initialized and return the value
			    st.value=GenerateER(st.name);      
		//	  st.att[i]=st.value;
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]=st.value; 
			}
			return result;			  
		}	
	else // st.name="EXP"
	{
		p = st.children;
		if(st.name.equals("add")) {
			l = ComputeNew(p);
			r = ComputeNew(p.sibling);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]=PVAL(l[i] + r[i]);
			}
			return result;
		} else if(st.name.equals("sub")) {
			l = ComputeNew(p);
			r = ComputeNew(p.sibling);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]=PVAL(l[i] - r[i]);
			}
			return result;
		} else if(st.name.equals("mul")) {
			l = ComputeNew(p);
			r = ComputeNew(p.sibling);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]=PVAL(l[i] * r[i]);
			}
			return result;
		} else if(st.name.equals("div")) {
			l = ComputeNew(p);
			r = ComputeNew(p.sibling);
			for(int i=0; i<NUMFITCASE; i++)
			{
				if(r[i] == 0) 
					{result[i]= 1;}
				else {result[i]= PVAL(l[i]/r[i]);}				
			}
			return result;
		
		} else if(st.name.equals("sin")) {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= Math.sin(l[i]);				
			}
			return result;
			
			
		} else if(st.name.equals("cos")) {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= Math.cos(l[i]);				
			}
			return result;
			
		} else if(st.name.equals("sqrt")) {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= Math.sqrt(Math.abs(l[i]));			
			}
			return result;			
		} else if(st.name.equals("sqr")) {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= PVAL(l[i]*l[i]);			
			}
			return result;
			
		} else if(st.name.equals("ep")) {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= PVAL(Math.exp(l[i]));	
			}
			return result;			
		}  else if(st.name.equals("divE")) {
			l = ComputeNew(p);
			r = ComputeNew(p.sibling);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= PVAL(l[i]/Math.sqrt(1+ r[i]*r[i]));				
			}
			return result;			
			} 
		else {
			l = ComputeNew(p);
			for(int i=0; i<NUMFITCASE; i++)
			{
				result[i]= PVAL(Math.exp(l[i]));
				if(l[i] == 0) result[i]= 0;
				else result[i]= PVAL(Math.log(Math.abs(l[i])));// fido fabs
			}
			return result;	
			
		}
	}
}

double[] ComputeTest(node st){
	node p;
	double[] l;
	double[] r;
	double[] result=new double[NUMFITTEST];
	
	for(int j=0;j<NUMVAR;j++){		
		
		if(st.name.equals("X"+String.valueOf(j+1))){
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]=fittest[i].x[j];
			}
			return result;
		}
	}
	
	if(st.name.equals("C")){
		for(int i=0; i<NUMFITTEST; i++)
		{
			result[i]=st.value;
		}
		return result;
	}
	else
		if(st.name.equals("ERC")) {
			if(st.value==VOIDVALUE) //if it has not been initialized then initialized and return the value
			    st.value=GenerateER(st.name);      
		//	  st.att[i]=st.value;
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]=st.value; 
			}
			return result;			  
		}	
	else // st.name="EXP"
	{
		p = st.children;
		if(st.name.equals("add")) {
			l = ComputeTest(p);
			r = ComputeTest(p.sibling);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]=PVAL(l[i] + r[i]);
			}
			return result;
		} else if(st.name.equals("sub")) {
			l = ComputeTest(p);
			r = ComputeTest(p.sibling);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]=PVAL(l[i] - r[i]);
			}
			return result;
		} else if(st.name.equals("mul")) {
			l = ComputeTest(p);
			r = ComputeTest(p.sibling);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]=PVAL(l[i] * r[i]);
			}
			return result;
		} else if(st.name.equals("div")) {
			l = ComputeTest(p);
			r = ComputeTest(p.sibling);
			for(int i=0; i<NUMFITTEST; i++)
			{
				if(r[i] == 0) result[i]= 1;
				else result[i]= PVAL(l[i]/r[i]);				
			}
			return result;
		
		} else if(st.name.equals("sin")) {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= Math.sin(l[i]);				
			}
			return result;
			
			
		} else if(st.name.equals("cos")) {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= Math.cos(l[i]);				
			}
			return result;
			
		} else if(st.name.equals("sqrt")) {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= Math.sqrt(Math.abs(l[i]));			
			}
			return result;			
		} else if(st.name.equals("sqr")) {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= PVAL(l[i]*l[i]);			
			}
			return result;
			
		} else if(st.name.equals("ep")) {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= PVAL(Math.exp(l[i]));	
			}
			return result;			
		} else if(st.name.equals("divE")) {
			l = ComputeTest(p);
			r = ComputeTest(p.sibling);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= PVAL(l[i]/Math.sqrt(1+r[i]*r[i]));				
			}
			return result;			
		}
		
		else {
			l = ComputeTest(p);
			for(int i=0; i<NUMFITTEST; i++)
			{
				result[i]= PVAL(Math.exp(l[i]));
				if(l[i] == 0) result[i]= 0;
				else result[i]= PVAL(Math.log(Math.abs(l[i])));// fido fabs
			}
			return result;	
			
		}
	}
}


	// *********************************************
	//Compute fitness for a individual
	
//Compute fitness for a individual
	double ComputeRF(individual st){
		double sum = 0.0,t;
		double[] tm;
		int i;
		int tp, tn, fp, fn;

		
		tp=0;
		tn=0;
		fp=0;
		fn=0;
		
		tm=ComputeNew(st.chrom);
		st.semanticTraining=tm;
		for(i = 0; i < NUMFITCASE; i++) {		
			t = PVAL(tm[i]);	
//			System.out.println("T: "+t);
			if(t<0) {
				
				if(fitcase[i].y<=0) {
					tn++;
				}
				else {
					fn++;
				}
			}
			else {
				if(fitcase[i].y<=0) {
					fp++;
				}
				else {
					tp++;
				}
				
			}				
			
		}
		if((tn+tp) == NUMFITCASE) SuccPredicate = TRUE;
		sum=fn+fp;
		sum=sum/NUMFITCASE;
		
		
		return sum;
	}
	
	double ComputeFT(individual st){
		double sum = 0, ft;
		int i;
		double[] tm;
		
		int tp, tn, fp, fn;

		
		tp=0;
		tn=0;
		fp=0;
		fn=0;
		
		tm=ComputeTest(st.chrom);
		for(i = 0; i < NUMFITTEST; i++) {
			ft = PVAL(tm[i]);
			if(ft<0) {
				
				if(fittest[i].y<=0) {
					tn++;
				}
				else {
					fn++;
				}
			}
			else {
				if(fittest[i].y<=0) {
					fp++;
				}
				else {
					tp++;
				}
				
			}	
//			
		}	
//		
		sum=fn+fp;
		sum=sum/NUMFITTEST;
		return sum;
	}
	
	

	double ComputeFT_AUC(individual st){
		double sum = 0, ft;
		int i;
		double[] tm;
		
		double tp, tn, fp, fn;

		
		tp=0;
		tn=0;
		fp=0;
		fn=0;
		
		tm=ComputeTest(st.chrom);
		for(i = 0; i < NUMFITTEST; i++) {
			ft = PVAL(tm[i]);
			if(ft<0) {
				
				AUC[0][i]=0;			
				if(fittest[i].y<=0) {
					tn++;					
					AUC[1][i]=0;
				}
				else {
					fn++;
					AUC[1][i]=1;
				}
			}
			else {
				AUC[0][i]=1;
				if(fittest[i].y<=0) {
					fp++;
					AUC[1][i]=0;
				}
				else {
					tp++;
					AUC[1][i]=1;
				}
				
			}	
//			
		}	
//		
	
		
		sum=fn+fp;
		sum=sum/NUMFITTEST;
		return sum;
	}
	
//compute two node distance
	
	
	double ComputeFT_F1Score(individual st){
		double sum = 0, ft;
		int i;
		double[] tm;
		
		int tp, tn, fp, fn;

		double precision, recall;
		tp=0;
		tn=0;
		fp=0;
		fn=0;
		
		tm=ComputeTest(st.chrom);
		for(i = 0; i < NUMFITTEST; i++) {
			ft = PVAL(tm[i]);
			
//			System.out.println("TM[i]:"+ft);
			if(ft<0) {				
				if(fittest[i].y<=0) {
					tn++;
				}
				else {
					fn++;
				}
			}
			else {
				if(fittest[i].y<=0) {
					fp++;
				}
				else {
					tp++;
				}
				
			}	
//			
		}	
//		
//		System.out.println("TP:"+tp);
//		System.out.println("FP:"+fp);
//		System.out.println("FN:"+fn);
//		System.out.println("TN:"+tn);
		precision=tp/(tp+fp);
		recall=tp/(tp+fn);
		sum=2*precision*recall/(precision+recall);
		return sum;
	}
	
//
//compute fitness for all individuals
	void ComputeFitness(){
		int i, pos;
		// individual t;
		double min, sum = 0, sumSize = 0, tm;
		// First Compute Raw fitness
		for(i = 0; i < poplen; i++)
		{
			if(oldpop[i].evaluated==FALSE)
			{   tm=ComputeRF(oldpop[i]);
				oldpop[i].fitness = tm;
				oldpop[i].oldfitness = tm;
				oldpop[i].evaluated=TRUE;
			}
			else
			{
				oldpop[i].fitness=oldpop[i].oldfitness;
			}
			
			//oldpop[i].DisplayIndividual();
			//System.out.println(oldpop[i].fitness);
		}
		//tim individual co fitness be nhat
		min = oldpop[0].fitness;
		pos = 0;
		sum = oldpop[0].fitness;
		sumSize = oldpop[0].size;
		
		for(i = 1; i < poplen; i++) {
			if(oldpop[i].fitness < min) {
				min = oldpop[i].fitness;
				pos = i;
			}
			sum += oldpop[i].fitness;
			sumSize += oldpop[i].size;
//			popSize[gen][i]= oldpop[i].size;
		}
		// copy the best and average
		bestcurrent[gen] = new individual();
		bestcurrent[gen].CopyIndividual(oldpop[pos], TRUE);
		average[gen] = sum /poplen;
		averageSize[gen] = sumSize /poplen;
		// Third Compute Adjusted fitness
		AdjustFitness();
		// Finally Compute nomarlized fitness
 		NormalizeFitness();
	}
	
//	
	
	//compute fitness for all individuals
	void ComputeFitnessBefore(){
				int i, pos;
				// individual t;
				double min, sum = 0,  tm;
				// First Compute Raw fitness
				for(i = 0; i < poplen; i++)
				{
					if(oldpop[i].evaluated==FALSE)
					{   tm=ComputeRF(oldpop[i]);
						oldpop[i].fitness = tm;
						oldpop[i].oldfitness = tm;
						oldpop[i].evaluated=TRUE;
					}
					else
					{
						oldpop[i].fitness=oldpop[i].oldfitness;
					}
					
					//oldpop[i].DisplayIndividual();
					//System.out.println(oldpop[i].fitness);
				}
				//tim individual co fitness be nhat
				min = oldpop[0].fitness;			
				sum = oldpop[0].fitness;			
				
				for(i = 1; i < poplen; i++) {
					if(oldpop[i].fitness < min) {
						min = oldpop[i].fitness;					
					}
					sum += oldpop[i].fitness;				
				}
				// copy the best and average
//				bestBeforeFitness[gen]=min;
//				avgBeforeFitness[gen]=sum/poplen;
//				bestcurrent[gen] = new individual();
//				bestcurrent[gen].CopyIndividual(oldpop[pos], TRUE);
//				average[gen] = sum /poplen;			
				
			}
		
//
	void Evolution(){
		int i, j, l, k;
		//individual[] temp;
		byte _flippc=0,_flippm=0;
//		String _temp1="", _temp2="";
		int[] _cp;
		node[] _nd,_nm;
		// Khoi tao quan the dau tien 
		RampedInit(6, 0.5);
		
		ncross = 0;
		
//		constructiveRate = new double[NUMGEN];
//		semanticDistance = new double[NUMGEN];
		
		gen = 0;
		while(gen < generation) {
			
			ComputeFitness();

			
//			int g_ncross = 0;
//			int g_numOfBetter = 0;
//			double g_sd = 0;
			
			l = 0;			
			while(l < poplen) {
				
//							
				i = TourSelect(TOURSIZE);
				j = TourSelect(TOURSIZE);
//					
			    _flippc=Flip(pcross);
//			    
				if(_flippc == 1) 
				{					
					individual[] i_temp=new individual[2];
					_cp=new int[2];
					_nd=new node[2];
					if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)
					{
						newpop[l].CopyIndividual(i_temp[0],TRUE);
						newpop[l+1].CopyIndividual(i_temp[1],TRUE);
						
						newpop[l].evaluated=FALSE;
						newpop[l+1].evaluated=FALSE;
						ncross++;
						
						
						i_temp=null;
						
//						if(newpop[l].isBetterThan(oldpop[i]))
//							g_numOfBetter += 1;
//						if(newpop[l+1].isBetterThan(oldpop[j]))
//							g_numOfBetter += 1;
//						
//						// semantic distance
//						g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//						g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);
//						
//						g_ncross++;
					}
					else
					{						
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);//					
						i_temp=null;
					} 
				} 
				else
				{
					newpop[l].CopyIndividual(oldpop[i], TRUE);
					newpop[l + 1].CopyIndividual(oldpop[j],TRUE);//									
				}
				// mutation test
				_flippm=Flip(pmutate);
//				System.out.printf("pmutation:%d", _flippm);
//			    System.out.println();
				if(_flippm == 1) {					
					individual[] m_individual=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					
					if(this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm)==TRUE)
					{
					newpop[l]=new individual();
					newpop[l].CopyIndividual(m_individual[0], TRUE);
					
					newpop[l].evaluated=FALSE;					
//									
					nmutate++;
					m_individual=null;
				
					}
				}
				
				
				if(Flip(pmutate) == 1) {
					individual[] m_individual1=new individual[1];
					int[] _mt=new int[1];
					_nm=new node[1];
					
					if(this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm)==TRUE)
					{
					newpop[l+1]=new individual();
					newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
					
					newpop[l+1].evaluated=FALSE;
//					
					nmutate++;
					m_individual1=null;
				
					}
					
					
				}
				l += 2;
			}

//			constructiveRate[gen] = g_numOfBetter / (2.0 * g_ncross);
//			semanticDistance[gen] = g_sd / (2.0 * g_ncross);
			
			gen++;
			for(k = 0; k < poplen; k++)
			{
				oldpop[k]=null;
				oldpop[k]=new individual();
				oldpop[k].CopyIndividual(newpop[k], TRUE);
			}
			
			
		}
		
	}

	
	//phuong thuc tien hoa
    void EvolutionWithElitism(){
		  	int i, j, l, k;
			//individual[] temp;
			byte _flippc=0,_flippm=0;
//			String _temp1="", _temp2="";
			int[] _cp;
			node[] _nd,_nm;
			// Khoi tao quan the dau tien 
			RampedInit(6, 0.5);
			//RampedInitOffLine(6, 0.5,15);
			
//			constructiveRate = new double[NUMGEN];
//			semanticDistance = new double[NUMGEN];
			
			gen = 0;
			while(gen < generation) {
				
				ComputeFitness();
				
//				int g_ncross = 0;
//				double g_sd = 0;
//				PrintToFile();
	//----------------------
//				FileInputStream instream = null;
//				PrintStream outstream = null;
//				PrintStream console = System.out;
//				try {
//					outstream = new PrintStream(new FileOutputStream("c:/result/" + "detail_"+ gen + ".txt"));
//					System.setOut(outstream);
//				} catch(Exception e) {
//					System.err.println("Error Occurred.");
//				}
//				for(i = 0; i < poplen; i++) {
//					System.out.printf("%3.5f  ",oldpop[i].fitness);
//					if((i+1) % 10 == 0) {
//						System.out.println();
//					}
//				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(oldpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
				
				// copy anh tot nhat sang the he sau	 	
				newpop[0].CopyIndividual(bestcurrent[gen], TRUE);
				// dot bien anh thu 2
			  
				l=1;	
				i = TourSelect(TOURSIZE);
//				
				
			    newpop[l].CopyIndividual(oldpop[i], TRUE);
				individual[] m_individual0=new individual[1];
				int[] _mt0=new int[1];
				_nm=new node[1];
				this.ReplaceSubTree(newpop[l], m_individual0, 15, TRUE,_mt0,_nm);
				newpop[l]=new individual();
				newpop[l].CopyIndividual(m_individual0[0], TRUE);			
				newpop[l].evaluated=FALSE;		
//				
//				m_individual0=null;
				
	//------------------------		
				l = 2;			
				while(l < poplen) {
				//	System.out.println("Generation "+ String.valueOf(gen)+":");
										
//					
					i = TourSelect(TOURSIZE);
					j = TourSelect(TOURSIZE);
					
//					
//						
				    _flippc=Flip(pcross);
//				    System.out.printf("pcross:%d", _flippc);
//				    System.out.println();
					if(_flippc == 1) {					
						individual[] i_temp=new individual[2];
						_cp=new int[2];
						_nd=new node[2];
				//	
				//		if(SubTreeSwapWithTTest(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)
						
						if(SubTreeSwap(oldpop[i], oldpop[j], i_temp,_cp,_nd) == TRUE)
						{
							newpop[l].CopyIndividual(i_temp[0],TRUE);
							newpop[l+1].CopyIndividual(i_temp[1],TRUE);
							
							newpop[l].evaluated=FALSE;
							newpop[l+1].evaluated=FALSE;
//							
							ncross++;
							i_temp=null;
							
							
							// semantic distance
//							g_sd += newpop[l].getSemanticDistance(oldpop[i]);
//							g_sd += newpop[l+1].getSemanticDistance(oldpop[j]);					
//							g_ncross++;
							
//							g_sd += oldpop[i].getSemanticDistance(oldpop[j]);					
//							g_ncross++;
							
						}
						else
						{						
							newpop[l].CopyIndividual(oldpop[i], TRUE);
							newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//							System.out.printf("%s","reproduction");
//							System.out.println();
//							_temp1="";
//							_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//							System.out.printf("%s", _temp1);			
//							System.out.println();
//							_temp2="";
//							_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//							System.out.printf("%s", _temp2);			
//							System.out.println();
							i_temp=null;
						} 
					} 
					else
					{
						newpop[l].CopyIndividual(oldpop[i], TRUE);
						newpop[l + 1].CopyIndividual(oldpop[j],TRUE);
//						System.out.printf("%s","reproduction");
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();					
					}
					// mutation test
					_flippm=Flip(pmutate);
//					System.out.printf("pmutation:%d", _flippm);
//				    System.out.println();
					if(_flippm == 1) {					
						individual[] m_individual=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l], m_individual, 15, TRUE,_mt,_nm);
						newpop[l]=new individual();
						newpop[l].CopyIndividual(m_individual[0], TRUE);
						
						newpop[l].evaluated=FALSE;
						
//						System.out.printf("%s","mutation "+l+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();					
						nmutate++;
						m_individual=null;
					}
					else
					{
//						_temp1="";
//						_temp1=newpop[l].chrom.TreeToStringN(newpop[l].chrom);
//						System.out.printf("%s", _temp1);			
//						System.out.println();
					}
					
					if(Flip(pmutate) == 1) {
						individual[] m_individual1=new individual[1];
						int[] _mt=new int[1];
						_nm=new node[1];
						this.ReplaceSubTree(newpop[l + 1], m_individual1, 15, TRUE,_mt,_nm);
						newpop[l+1]=new individual();
						newpop[l+1].CopyIndividual(m_individual1[0], TRUE);	
						
						newpop[l+1].evaluated=FALSE;
//						System.out.printf("%s","mutation "+(l+1)+" replacepoint:"+_mt[0]);
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(_nm[0]);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
						nmutate++;
						m_individual1=null;
					}
					else
					{
//						_temp2="";
//						_temp2=newpop[l+1].chrom.TreeToStringN(newpop[l+1].chrom);
//						System.out.printf("%s", _temp2);			
//						System.out.println();
					}
					l += 2;
				}
//				for(int ii = 0; ii < poplen; ii++) {			
//					_temp1="";
//					_temp1=oldpop[ii].chrom.TreeToStringN(newpop[ii].chrom);
//					System.out.printf("%s",ii+" "+ _temp1);			
//					System.out.println();
//				}
//				outstream.close();
//				System.setOut(console);
				// Thống kê tỷ lệ tTest
				
			
				
	//			semanticDistance[gen] = g_sd / (2.0 * g_ncross);
//				semanticDistance[gen] = g_sd /g_ncross;
				
				gen++;
				for(k = 0; k < poplen; k++)
				{
					oldpop[k]=null;
					oldpop[k]=new individual();
					oldpop[k].CopyIndividual(newpop[k], TRUE);
				}
			}
		}
	//
	

}
