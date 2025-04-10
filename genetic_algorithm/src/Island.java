import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Island {
    private Chromosome currentBest;
    private Chromosome currentSecond;
    private Island next;
    private Island previous;
    private double totalFixedFitness;
    private double minFitness;
    public double maxFitness;
    public List<Chromosome> chromosomes = new ArrayList<>();
    public Island(int islandSize,int genesNumber,double lowerlimit,double upperlimit, int decimalplaces) {
        for(int i=0;i<islandSize;i++)
        {
            Chromosome chromosome = new Chromosome(genesNumber,lowerlimit,upperlimit,decimalplaces);
            chromosomes.add(chromosome);
        }
        getMinFitness();
        getTotalFitness();
        getBest();
    }

    @Override
    public String toString() {
    String a="";
    for(Chromosome c : chromosomes)
        a+=c.toString()+"\n";
    return a;
    }

    public void getTotalFitness() {
        double help=0;
        for(Chromosome c : chromosomes)
            help+=c.fitnessValue-minFitness;
        totalFixedFitness=help;
    }
    public void getMinFitness() {
        double help=chromosomes.get(0).fitnessValue;
        for(Chromosome c: chromosomes)
            if(c.fitnessValue<help)
                help=c.fitnessValue;
        minFitness=help;
      ///  System.out.println("min= "+minFitness);
    }

    public void getBest(){
        double help=chromosomes.get(0).fitnessValue;
        for(Chromosome c: chromosomes)
            if(c.fitnessValue>help)
                help=c.fitnessValue;
        maxFitness=help;
    }

    public void rouletteCrossing(){
     ///   System.out.println("total " + totalFixedFitness);
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        Random rand = new Random();
        double sum = 0;
        for(int i=0;i<chromosomes.size();i++) {
            double help;
            help = rand.nextDouble() * totalFixedFitness;
          ///  System.out.println("help "+ help);
            sum = 0;
            for (Chromosome chr : chromosomes) {
                sum += chr.fitnessValue - minFitness;
//                System.out.println("sum "+sum);
//                System.out.println("current chromosome" + chr);
                if(selectedChromosomes.size()==0)
                {
                    if (sum >= help ) {
                        selectedChromosomes.add(chr);
                        //   System.out.println("chromosome added");
                        break;
                    }
                }
                else if(chr.fitnessValue==chromosomes.get(chromosomes.size()-1).fitnessValue&&chr.fitnessValue == selectedChromosomes.get(selectedChromosomes.size() - 1).fitnessValue)
                {
                        selectedChromosomes.add(chromosomes.get(0));
                     ///   System.out.println("chromosome  added");
                        break;
                }
                else {
                    if (sum >= help && chr.fitnessValue != selectedChromosomes.get(selectedChromosomes.size() - 1).fitnessValue) {
                        selectedChromosomes.add(chr);
                    ///    System.out.println("chromosome added");
                        break;
                    }
                }
            }
        }
       /// for(Chromosome c:selectedChromosomes)
          ///  System.out.println("---"+c);
        List<Chromosome> childrenChromosomes = new ArrayList<>();
        Chromosome[]children = new Chromosome[2];
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<selectedChromosomes.size()-1;i+=2)
        {
            children=Chromosome.crossing(selectedChromosomes.get(i),selectedChromosomes.get(i+1));
            //children[0].mutate(Main.alpha);
            //children[1].mutate(Main.alpha);
            sb.delete(0,sb.length());
            sb.append((children[0].gene_binary[0]));
            while(sb.length() < 18.2) /////////////////////////////////////////////////////////////////zmien to chuju
                sb.insert(0, '0');
            childrenChromosomes.add(children[0]);
            childrenChromosomes.get(childrenChromosomes.size()-1).gene_binary[0]=sb.toString();
            sb.delete(0,sb.length());
            sb.append((children[0].gene_binary[1]));
            while(sb.length() < 18.2) /////////////////////////////////////////////////////////////////zmien to chuju
                sb.insert(0, '0');
            childrenChromosomes.get(childrenChromosomes.size()-1).gene_binary[1]=sb.toString();
            childrenChromosomes.add(children[1]);
            sb.delete(0,sb.length());
            sb.append((children[1].gene_binary[0]));
            while(sb.length() < 18.2) /////////////////////////////////////////////////////////////////zmien to chuju
                sb.insert(0, '0');
            childrenChromosomes.get(childrenChromosomes.size()-1).gene_binary[0]=sb.toString();
            sb.delete(0,sb.length());
            sb.append((children[1].gene_binary[1]));
            while(sb.length() < 18.2) /////////////////////////////////////////////////////////////////zmien to chuju
                sb.insert(0, '0');
            childrenChromosomes.get(childrenChromosomes.size()-1).gene_binary[1]=sb.toString();
        }
        for(Chromosome c : childrenChromosomes){
            c.mutate(Main.alpha);
            c.correctChild();//////////////////////////////////////////////////////////dlaczego to powoduje błędy halooooooooo
            c.encode();
        }
        for(int i=0;i<chromosomes.size();i++)
        {
            Chromosome.overwriteChromosome(chromosomes.get(i),childrenChromosomes.get(i));
        }
       getMinFitness();
        getTotalFitness();
        getBest();
    }

}
