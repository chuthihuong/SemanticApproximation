

package DA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;


import Common.Class_Random;;

public class MainDA extends Class_Random {
	public MainDA() {}

	int									i, pos;
	double								min;
	long								StartSeed		= 1900;
	Population							myPop;
	int nrun=NRUN;
	long Seed=0;
	
	double average=0;
	double fittest, fitness;
	double averagetest=0;
	double averagesize=0;
	

	String outDir = OUTDIR + "DA20/";
	
//	public void TestGenotype(){
//		myPop = new Population(Seed);
//		myPop.TestGentype();
//	}
	public void MStart()
	{
		
		PrintStream f1 = null;
		PrintStream f2 = null;
//		PrintStream f3 = null;
		PrintStream console = System.out;
		try 
		{
			FileOutputStream fos2 = new FileOutputStream(new File(outDir + PROBLEM + ".run.txt"), true);
			f2 = new PrintStream(fos2);
			
//			FileOutputStream fos3 = new FileOutputStream(new File(outDir + PROBLEM + ".SizeAll.txt"), true);
//			f3 = new PrintStream(fos3);			
			
			
		} 
		catch(Exception e) 
		{
			System.err.println("Output files error!");
	      
		}
		
		
		int run = 20;
		Seed=StartSeed+run;
		
		double[] fittests = new double[NRUN];	
		
		// Bloat, Overfit. Complexity
		double tu,mau;
		double btp, tbtp, overfit;	
		
//	
		int[][] Indexfeature =new int[NUMFITCASE][NUMVAR];
		int[][] IndexUniquefeature =new int[NUMFITCASE][NUMVAR];
		int[]   NumUniquefeature=new int[NUMVAR];
//	
		
		while (Seed<StartSeed+nrun)
		{
			
			System.err.println("Run: " + Seed);

			Seed++;
			
			
			long starttime = System.currentTimeMillis();
			
			myPop = new Population(Seed);
			
			myPop.EvolutionWithElitismWithTrunc(100, 0.2);

			starttime = System.currentTimeMillis() - starttime;

			// Read Data for compute complexity
			for(i=0;i<NUMFITCASE;i++){
				for(int j=0; j<NUMVAR;j++ ){
//					feature[i][j]=myPop.fitcase[i].x[j];
					Indexfeature[i][j]=i;
				}
			}			

			
			// Sort feature
			for(int i1=0;i1<NUMFITCASE-1;i1++){
				for(int j1=i1+1;j1<NUMFITCASE;j1++){
					for(int k1=0;k1<NUMVAR;k1++){						
						if(myPop.fitcase[Indexfeature[i1][k1]].x[k1]>myPop.fitcase[Indexfeature[j1][k1]].x[k1]){
							i=Indexfeature[i1][k1];
							Indexfeature[i1][k1]=Indexfeature[j1][k1];
							Indexfeature[j1][k1]=i;
						}
					}
				}
			}
			
//			for(int j1=0;j1<NUMVAR;j1++){
//			
//				System.out.println();
//				for(int i1=0;i1<NUMFITCASE;i1++){
//					System.out.print(myPop.fitcase[Indexfeature[i1][j1]].x[j1]+" ");
//				}
//			
//			}
			
			// Filter for unique values foreach feature.
			for(int k=0; k<NUMVAR;k++){
				NumUniquefeature[k]=0;
				IndexUniquefeature[0][k]=Indexfeature[0][k];
				for(int j=1;j<NUMFITCASE;j++){
					if(myPop.fitcase[IndexUniquefeature[NumUniquefeature[k]][k]].x[k]!=myPop.fitcase[Indexfeature[j][k]].x[k]){
						NumUniquefeature[k]++;
						IndexUniquefeature[NumUniquefeature[k]][k]=Indexfeature[j][k];
					}
				}
			}
			
//			System.out.println("After:");
//			for(int j1=0;j1<NUMVAR;j1++){				
//				System.out.println();
//				for(int i1=0;i1<=NumUniquefeature[j1];i1++){
//					System.out.print(myPop.fitcase[IndexUniquefeature[i1][j1]].x[j1]+" ");
//				}
//			
//			}
			// Compute Complexity at generation 0
			 double t1,t2,complexity;
			 complexity=0;
			 for(int k=0; k<NUMVAR; k++){
				 t1= (myPop.bestcurrent[0].semanticTraining[IndexUniquefeature[1][k]]- 
						 myPop.bestcurrent[0].semanticTraining[IndexUniquefeature[0][k]]) 
						 /(myPop.fitcase[IndexUniquefeature[1][k]].x[k]-myPop.fitcase[IndexUniquefeature[0][k]].x[k]);
				
				 for(int j=1;j<NumUniquefeature[k];j++){
					 t2= (myPop.bestcurrent[0].semanticTraining[IndexUniquefeature[j+1][k]]- 
							 myPop.bestcurrent[0].semanticTraining[IndexUniquefeature[j][k]]) 
							 /(myPop.fitcase[IndexUniquefeature[j+1][k]].x[k]-myPop.fitcase[IndexUniquefeature[j][k]].x[k]);
					 complexity+=Math.abs(t2-t1);
					 t1=t2;
				 }
			 }
			 complexity=complexity/NUMVAR;
	//		 System.out.println("Complexity: "+complexity);
			 
			
			
			//===============
			min = myPop.bestcurrent[0].fitness;			
			pos = 0;
			
			// print results to file
			try 
			{			
				FileOutputStream fos1 = new FileOutputStream(new File(outDir +PROBLEM+"/"+PROBLEM+"_"+run + ".gen.txt"), true);
				f1 = new PrintStream(fos1);						
			} 
			catch(Exception e) 
			{
				System.err.println("Output files"+outDir + PROBLEM + ".all.txt" +"error!");
		      
			}
			
			
			//compete Complexity
//			
//			for(int k=0;k<NUMVAR;k++){
//				double t1=0,t2=0,s=0,temp;
//				int firstPoint, SecondPoint;
//				firstPoint=0;
//				SecondPoint=firstPoint+1;
//				while(SecondPoint<NUMFITCASE){
//					temp=myPop
//				}
//				
//			}
			//===============
			
			f1.print("0;");
			
			fitness=myPop.bestcurrent[0].fitness;
			f1.print(fitness+";");
			
			fittest=myPop.ComputeFT(myPop.bestcurrent[0]);
			f1.print(fittest+";");
			
			f1.print(myPop.bestcurrent[0].size+";");			
			f1.print(myPop.averageSize[0]+";");
		
			f1.print(myPop.average[0]+";");			
//			f1.print(myPop.constructiveRate[0]+";");
//			f1.print(myPop.semanticDistance[0]+";");
//			f1.print(myPop.genotypeDist[0]+";");
			
			// Bloat
			f1.print(0.0+";");
			// overfit
			f1.print(0.0+";");
			// Complexity
			f1.print(complexity+";");			
			f1.println(myPop.bestBeforeFitness[0]+";");
			
			
			
			btp=fittest;
			tbtp=fitness;
			
			
		
			
			
			for(i = 1; i < NUMGEN; i++)
			{
				f1.print(i+";");
				
				fitness=myPop.bestcurrent[i].fitness;
				f1.print(fitness+";");
				
				fittest=myPop.ComputeFT(myPop.bestcurrent[i]);
				f1.print(fittest+";");
				
				f1.print(myPop.bestcurrent[i].size+";");			
				f1.print(myPop.averageSize[i]+";");
			
				f1.print(myPop.average[i]+";");			
//				f1.print(myPop.constructiveRate[i]+";");
//				f1.print(myPop.semanticDistance[i]+";");
//				f1.print(myPop.genotypeDist[i]+";");
				
				// Print Bloat
				tu=(myPop.averageSize[i]-myPop.averageSize[0])/myPop.averageSize[0];
				mau=(myPop.average[0]-myPop.average[i])/myPop.average[0];
				f1.print((tu/mau)+";");
				
				// Print Overfit
				if(fitness>fittest){
					overfit=0;
				} else {
					if(fittest<btp){
						overfit=0;
						btp=fittest;
						tbtp=fitness;
					} 
					else
					{
						overfit=Math.abs(fitness-fittest)-Math.abs(tbtp-btp);
					}
				}
				
				f1.print(overfit+";");
				
				//complexirty
				
				complexity=0;
				 for(int k=0; k<NUMVAR; k++){
					 t1= (myPop.bestcurrent[i].semanticTraining[IndexUniquefeature[1][k]]- 
							 myPop.bestcurrent[i].semanticTraining[IndexUniquefeature[0][k]]) 
							 /(myPop.fitcase[IndexUniquefeature[1][k]].x[k]-myPop.fitcase[IndexUniquefeature[0][k]].x[k]);
					
					 for(int j=1;j<NumUniquefeature[k];j++){
						 t2= (myPop.bestcurrent[i].semanticTraining[IndexUniquefeature[j+1][k]]- 
								 myPop.bestcurrent[i].semanticTraining[IndexUniquefeature[j][k]]) 
								 /(myPop.fitcase[IndexUniquefeature[j+1][k]].x[k]-myPop.fitcase[IndexUniquefeature[j][k]].x[k]);
						 complexity+=Math.abs(t2-t1);
						 t1=t2;
					 }
				 }
				 complexity=complexity/NUMVAR;
				 
				 
				 f1.print(complexity+";");				 
				 f1.println(myPop.bestBeforeFitness[i]+";");
				//===========
				
				if(myPop.bestcurrent[i].fitness < min) 
				{
					min = myPop.bestcurrent[i].fitness;
					pos = i;
			     }
				
				
		     }			
			
//			for(int g = 0; g < NUMGEN; g++){
//				for(i=0;i<POPSIZE;i++){
//					f3.println(g+","+myPop.popSize[g][i]);
//				}
//				
//			}
			
			
			average+=myPop.bestcurrent[pos].fitness;
			fittest=myPop.ComputeFT(myPop.bestcurrent[pos]);
			fittests[run] = fittest;
			
			//averagetest+=fittest;
			averagesize+=myPop.bestcurrent[pos].size;
			
			
			f2.println(myPop.bestcurrent[pos].fitness + " " + fittest 
					+ " " + myPop.bestcurrent[pos].size + " " + starttime + "\n");
			
			run += 1;
	   }	
		
		f1.close();
		f2.close();
//		f3.close();		
		System.setOut(console);
						
}

    public static void main(String[] args)
    {
		MainDA a = new MainDA();	
		System.out.printf("Main GP Start ABV: "+ PROBLEM + "\n");
		
		a.MStart();
		
	//	a.TestGenotype();
		
		System.out.printf("Finish!");
		
	}
}
