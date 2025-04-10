import java.util.Random;
import java.util.function.Function;
public class Chromosome {

    public String[] gene_binary;
    public double[] gene_value;
   /// public double[] decoded_value;
    private static Random rand;
    private static int numberOfgenes;
    private static double minNumberOfBits;
    public static double lowerLimit;
    private static double upperLimit;
    private static int decimalPlaces;
    public double fitnessValue;



    public Chromosome(int genesNumber,double lowerlimit,double upperlimit, int decimalplaces) {
        gene_binary=new String[genesNumber];
        gene_value=new double[genesNumber];
        ///decoded_value=new double[genesNumber];
        numberOfgenes=genesNumber;
        upperLimit=upperlimit;
        lowerLimit=lowerlimit;
        decimalPlaces=decimalplaces;
        minNumberOfBits = log2(2,(upperLimit-lowerLimit)*Math.pow(10,decimalPlaces));
        generate_genes(lowerLimit,upperLimit,decimalPlaces);
        encode();
        decode();
    }
    private void generate_genes(double lowerLimit,double upperLimit, int decimalPlaces) {
        rand = new Random();
        for (int i = 0; i < numberOfgenes; i++) {
            gene_value[i] = rand.nextDouble() * (upperLimit - lowerLimit) + lowerLimit;
            gene_value[i] *= Math.pow(10, decimalPlaces);
            gene_value[i] = Math.round(gene_value[i]);
            gene_value[i] /= Math.pow(10, decimalPlaces);
        }
    }
    public void encode() {
        double help;

        for(int i = 0; i < numberOfgenes; i++){
            StringBuilder sb = new StringBuilder();
            help = (gene_value[i]-lowerLimit)*Math.pow(10,decimalPlaces);
            sb.append(Long.toBinaryString(Math.round(help)));

            while(sb.length() < minNumberOfBits)
                sb.insert(0, '0');

            gene_binary[i] = sb.toString();
        }
        setFitness(Main.fun);
    }
    public void decode(){
        double help;

        for(int i = 0; i < numberOfgenes; i++){
            help = Integer.parseInt(gene_binary[i], 2);
            gene_value[i] = help/Math.pow(10,decimalPlaces)+lowerLimit;

            gene_value[i] *= Math.pow(10, decimalPlaces);
            gene_value[i] = Math.round(gene_value[i]);
            gene_value[i] /= Math.pow(10, decimalPlaces);
            setFitness(Main.fun);

        }
    }
    public double log2(double base,double argument){
        return Math.log(argument) / Math.log(base);
    }
    @Override
    public String toString() {
        String a="";
        for(int i=0;i<numberOfgenes;i++)
            a+=this.gene_value[i]+"\t"+gene_binary[i]+"\t"+fitnessValue+"\n";
        return a;
    }
    public static Chromosome[] crossing(Chromosome p1, Chromosome p2) {
        Chromosome child1 = new Chromosome(p1.numberOfgenes, p1.lowerLimit, p1.upperLimit, p1.decimalPlaces);
        Chromosome child2 = new Chromosome(p2.numberOfgenes, p2.lowerLimit, p2.upperLimit, p2.decimalPlaces);

        StringBuilder p1Representation = new StringBuilder();  //parent 1 as a string
        StringBuilder p2Representation = new StringBuilder(); //parent 2 as a string

        StringBuilder ch1Representation = new StringBuilder();  //child 1 as a string
        StringBuilder ch2Representation = new StringBuilder(); //child 2 as a string

        for(String g : p1.gene_binary)
        p1Representation.append(g);

        for(String g : p2.gene_binary)
            p2Representation.append(g);

        rand = new Random();
        int range = p1.gene_binary[0].length()*p1.numberOfgenes;
        int crossingPoint=rand.nextInt(range);

      //  System.out.println("pkt "+crossingPoint);

        ch1Representation.append(p1Representation.substring(0,crossingPoint)).append(p2Representation.substring(crossingPoint));
        ch2Representation.append(p2Representation.substring(0,crossingPoint)).append(p1Representation.substring(crossingPoint));

        int start=0;
        int finish=range/child1.numberOfgenes;
        for (int i=0;i< child1.numberOfgenes;i++)
        {

            child1.gene_binary[i]=ch1Representation.substring(start,finish);
            child2.gene_binary[i]=ch2Representation.substring(start,finish);

            start=finish;
            finish+=range/child1.numberOfgenes;
        }

        child1.decode();
        child2.decode();
        child1.correctChild();
        child2.correctChild();

        return new Chromosome[]{child1,child2};
    }
    void correctChild() {
        for (int i = 0; i < numberOfgenes; i++) {

            if (this.gene_value[i] < lowerLimit)
                this.gene_value[i] = lowerLimit;
            if (this.gene_value[i] > upperLimit)
                this.gene_value[i] = upperLimit;
        }
        encode();
    }
    void setFitness(Function<Double[], Double> f){
        Double[] args = new Double[numberOfgenes];
        for(int i=0;i<numberOfgenes;i++)
            args[i]=gene_value[i];
      this.fitnessValue=f.apply(args);
    }
    void mutate (double alpha){
        Random rand = new Random();
        StringBuilder help = new StringBuilder();
        for(int i=0;i<numberOfgenes;i++)
            help.append(gene_binary[i]);

        char[] chromosome = help.toString().toCharArray();
        help.setLength(0);
        for (char bit:chromosome) {
            if(rand.nextDouble() < alpha)
                help.append(bit == '0' ? '1' : '0');
            else
                help.append(bit);
        }

        int range = this.gene_binary[0].length()*this.numberOfgenes;
        int start=0;
        int finish=range/this.numberOfgenes;
        for (int i=0;i< this.numberOfgenes;i++)
        {
            this.gene_binary[i]=help.substring(start,finish);
            start=finish;
            finish+=range/this.numberOfgenes;
        }
        decode();
       }
    public static void overwriteChromosome(Chromosome a, Chromosome b) {
       for(int i=0;i<numberOfgenes;i++){
         a.gene_binary[i]=b.gene_binary[i];
         a.gene_value[i]=b.gene_value[i];
       }
        a.numberOfgenes=b.numberOfgenes;
        a.minNumberOfBits=b.minNumberOfBits;
        a.lowerLimit=b.lowerLimit;
        a.upperLimit=b.lowerLimit;
        a.decimalPlaces=b.decimalPlaces;
        a.fitnessValue=b.fitnessValue;

    }
    }

