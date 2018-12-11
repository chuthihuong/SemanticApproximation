package Common;

public interface Const {
	public static final int		MAXSTRING	= 60000;				// max of a tring (for _sbuffer)
	public static final byte	TRUE			= 1;
	public static final byte	FALSE			= 0;
	public static final int		MAXNAME		= 6;						// max+1 length of name of a symbol
	public static final int		MAXDEPTH		= 17;					// max size of chromosome
	public static final int		MAXTOUR		= 10;					// max tournement size
	public static final int		MAXATEMPT	= 100;					// max atempt for choosing a site of a chromosome
	public static final int		MAXFUNCTION	= 20;
	public static final int		MAXTERMINAL	= 40;
	public static final int		MAXNODE		= 3000;
	public static final double	VOIDVALUE	= -1523612789.21342;
	
	
	public static final int	    NUMGEN=100;
	public static final int		POPSIZE=500;
	public static final int		TOURSIZE=3;
	
	public static final int 	NRUN=30;
	
	
	
	public static final int		NUMCOPY=2;
	public static final int		MAXSTEPGRADIENT=200;
	
		
	public final double PCROSS = 0.9;
	public final double PMUTATE = 0.1;
	
	public final double PPrunePlant = 0.5;
	public final double PSAT = 0.5;
	
	public final int TREELIBSIZE = 1000;
	public final int TREELIB_MAXDEPTH = 2;
	public final double STAR = Double.NaN;
	
	public final boolean KOZA_MUTATION = true;
	
	
	public final double Epsilon = 0.000001;
	


//	public static final String PROBLEM = "Protein_Tertiary_Structure";//11 300 300
//	public static final int		NUMVAR	=9;
//	public static final int		NUMFITCASE	= 1000;
//	public static final int		NUMFITTEST	= 1000;	
	
//	public static final String PROBLEM = "ccpp";//11 250 250
//	public static final int		NUMVAR	=4;
//	public static final int		NUMFITCASE	=1000;
//	public static final int		NUMFITTEST	=1000;
	
	
//	public static final String PROBLEM = "airfoil_self_noise";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=800;
//	public static final int		NUMFITTEST	=703;
	

	
//	public static final String PROBLEM = "concrete";//11 250 250
//	public static final int		NUMVAR	=8;
//	public static final int		NUMFITCASE	=500;
//	public static final int		NUMFITTEST	=530;
	
	
//	public static final String PROBLEM = "casp";//11 250 250
//	public static final int		NUMVAR	=9;
//	public static final int		NUMFITCASE	=100;
//	public static final int		NUMFITTEST	=100;
	
//	public static final String PROBLEM = "wpbc";//11 300 300
//	public static final int		NUMVAR	=31;
//	public static final int		NUMFITCASE	= 100;
//	public static final int		NUMFITTEST	= 98;
	
	
	
	
//	public static final String PROBLEM = "slump_test_Compressive";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	=53;
	
//	public static final String PROBLEM = "slump_test_FLOW";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	=53;
	
//	public static final String PROBLEM = "slump_test_SLUMP";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	=53;
	
	

	
//	public static final String PROBLEM = "vladislavleva-2";//11 250 250
//	public static final int		NUMVAR	=1;
//	public static final int		NUMFITCASE	=100;
//	public static final int		NUMFITTEST	=221;
	
//	public static final String PROBLEM = "vladislavleva-4";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=500;
//	public static final int		NUMFITTEST	=500;
//	
	
//	public static final String PROBLEM = "vladislavleva-6";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=30;
//	public static final int		NUMFITTEST	=93636;
	
	public static final String PROBLEM = "vladislavleva-8";//11 250 250
	public static final int		NUMVAR	=2;
	public static final int		NUMFITCASE	=50;
	public static final int		NUMFITTEST	= 1089;	
	
	// korn 1,2,3,4,
	//2,3 11,12,14,15
	
//		public static final String PROBLEM = "korns-2";//11 300 300
//		public static final int		NUMVAR	=5;
//		public static final int		NUMFITCASE	= 1000;
//		public static final int		NUMFITTEST	= 1000;
//	
	
//	
	
//	public static final String PROBLEM = "korns-14-20";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=20;
//	public static final int		NUMFITTEST	=20;
		
//	public static final String PROBLEM = "3D_spatial_network";//11 300 300
//	public static final int		NUMVAR	=3;
//	public static final int		NUMFITCASE	= 750;
//	public static final int		NUMFITTEST	= 750;
		
//		public static final String PROBLEM = "winequality-red";//11 250 250
//		public static final int		NUMVAR	=11;
//		public static final int		NUMFITCASE	=800;
//		public static final int		NUMFITTEST	= 799;	
		
//		public static final String PROBLEM = "winequality-white";//11 250 250
//		public static final int		NUMVAR	=11;
//		public static final int		NUMFITCASE	=1000;
//		public static final int		NUMFITTEST	= 1000;	
		
//	public static final String PROBLEM = "ISTANBUL_STOCK_EXCHANGE";//11 300 300
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	= 270;
//	public static final int		NUMFITTEST	= 266;	

//	public static final String PROBLEM = "yacht_hydrodynamics";//11 300 300
//	public static final int		NUMVAR	=6;
//	public static final int		NUMFITCASE	= 160;
//	public static final int		NUMFITTEST	= 148;
		
	
//	public static final String PROBLEM = "airfoil_self_noise";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=800;
//	public static final int		NUMFITTEST	= 703;
	
//	public static final String PROBLEM = "casp";//11 250 250
//	public static final int		NUMVAR	=9;
//	public static final int		NUMFITCASE	=100;
//	public static final int		NUMFITTEST	= 100;
	
//	public static final String PROBLEM = "ccpp";//11 250 250
//	public static final int		NUMVAR	=4;
//	public static final int		NUMFITCASE	=1000;
//	public static final int		NUMFITTEST	= 1000;
	
//	public static final String PROBLEM = "concrete";//11 250 250
//	public static final int		NUMVAR	=8;
//	public static final int		NUMFITCASE	=500;
//	public static final int		NUMFITTEST	= 530;
	
//	public static final String PROBLEM = "korns-14";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=20;
//	public static final int		NUMFITTEST	= 20;

//	public static final String PROBLEM = "slump_test_Compressive";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	= 53;
	
//	public static final String PROBLEM = "slump_test_FLOW";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	= 53;
	
//	public static final String PROBLEM = "slump_test_SLUMP";//11 250 250
//	public static final int		NUMVAR	=7;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	= 53;	
	
//	public static final String PROBLEM = "vladislavleva-1";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=20;
//	public static final int		NUMFITTEST	= 2025;
	
//	public static final String PROBLEM = "vladislavleva-2";//11 250 250
//	public static final int		NUMVAR	=1;
//	public static final int		NUMFITCASE	=100;
//	public static final int		NUMFITTEST	= 221;
	
//	public static final String PROBLEM = "vladislavleva-3";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=20;
//	public static final int		NUMFITTEST	= 5083;
	
//	public static final String PROBLEM = "vladislavleva-4";//11 250 250
//	public static final int		NUMVAR	=5;
//	public static final int		NUMFITCASE	=500;
//	public static final int		NUMFITTEST	= 500;	
	
//	public static final String PROBLEM = "vladislavleva-5";//11 250 250
//	public static final int		NUMVAR	=3;
//	public static final int		NUMFITCASE	=300;
//	public static final int		NUMFITTEST	= 2640;	
	
//	public static final String PROBLEM = "vladislavleva-6";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=30;
//	public static final int		NUMFITTEST	= 93636;	
	
//	public static final String PROBLEM = "vladislavleva-7";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=300;
//	public static final int		NUMFITTEST	= 1000;	
	
//	public static final String PROBLEM = "vladislavleva-8";//11 250 250
//	public static final int		NUMVAR	=2;
//	public static final int		NUMFITCASE	=50;
//	public static final int		NUMFITTEST	= 1089;	
	
	// Problems	

//		public static final String PROBLEM = "ViewSumStore3_120";//11 300 300
//		public static final int		NUMVAR	=7;
//		public static final int		NUMFITCASE	= 1549;
//		public static final int		NUMFITTEST	= 121;	
	
//	
//	public static final String PROBLEM = "wpbc";//11 250 250
//	public static final int		NUMVAR	=31;
//	public static final int		NUMFITCASE	=100;
//	public static final int		NUMFITTEST	= 98;
	
	public static final int		NUMFOLD	= 5;
	public static final int		NUMBOOTSTRAP= 5;

	public final String DATADIR = "../Data/DataTest/";
	public final String OUTDIR = "../Result/";	
	
	
	public static final double	INFPLUS		=  Math.exp(300);
	public static final double	INFMINUS		= -Math.exp(300);
	public static final double	HUGE_VAL		= Math.exp(300);
}
