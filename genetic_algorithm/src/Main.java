import java.util.function.Function;
public class Main {
    public static double alpha=0.1;
    public static Function<Double[], Double> fun = (arg) -> Math.pow(arg[0], 2) + Math.pow(arg[1],2) ;
    public static void main(String[] args) {
        Chromosome ch1 = new Chromosome(2,-2,2,5);
        Chromosome ch2 = new Chromosome(2,-2,2,5);
        Chromosome ch3 = new Chromosome (2,-1,1,1);
        Island i1 = new Island(10,2,-2,2,5);
    //    Island i2 = new Island(1000000,2,-2,2,5);
   //     System.out.println(i2.maxFitness);
        System.out.println(i1);
        for(int i=0;i<10;i++){
        i1.rouletteCrossing();
        System.out.println("//////////////////////////////population "+i);
        System.out.println("maxfitness "+i1.maxFitness);
           System.out.println(i1);
        }


//      System.out.println(ch1);
//        ch3.mutate(0.1);
//        System.out.println(ch3);
    }
}