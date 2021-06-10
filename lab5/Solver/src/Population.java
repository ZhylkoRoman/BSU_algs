import java.util.*;

public class Population {
    public static final int VALUE_ACHIEVED = -5;
    public static final int VALUE_NOT_ACHIEVED = -6;

    private Gene[] population = new Gene[5];

    public void createInitialPopulation() {
        for (int i = 0; i < population.length; i++) {
            population[i] = new Gene();
            createGenomeWithRandomValues(population[i]);
        }
    }

    private void createGenomeWithRandomValues(Gene gene) {
        System.out.println("Заполнение генов случайными значениями");

        for (int i = 0; i < 5; i++) {
            gene.getGenome()[i] = createGenomeWithRandom();
            System.out.println("Ген " + i + " = " + gene.getGenome()[i]);
        }

        System.out.println();
    }

    public static int createGenomeWithRandom() {
        return getRandomNumberInRange(-200, 200);
    }

    private static int getRandomNumberInRange(int min, int max) {
        return min + (int) ((1 + max - min) * Math.random());
    }

    public int fillGenomesWithFitness() {
        System.out.println("Вычисление приближенности значений функций генов");

        for (int i = 0; i < 5; i++) {
            float currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            System.out.println("Приспособленность гена " + i + "= " + population[i].getFitness());

            if (currentFitness == VALUE_ACHIEVED) return i;
        }

        System.out.println();
        return VALUE_NOT_ACHIEVED;
    }

    public Gene[][] selection(){
        System.out.println("Селекция");
        Gene[][] pairs = new Gene[5][2];

        for (int i = 0; i < 5; i++){
            pairs[i] = randomSelection(population);
        }

        System.out.println();
        return pairs;
    }

    private Gene[] randomSelection(Gene[] population) {
        List<Gene> pair = new ArrayList<>();
        pair.add(population[randomGenomByProportion(population)]);
        pair.add(population[randomGenomByProportion(population)]);

        while(pair.get(0)==pair.get(1)){
            pair.set(1, population[getRandomNumberInRange(0, 4)]);
        }

        System.out.println("Полученная пара родителей:" + Arrays.toString(findBest(pair)));
        return findBest(pair);
    }

    private Integer randomGenomByProportion(Gene[] population){
        float[] boundaries = new float[6];
        for (int i = 1; i < boundaries.length; i++) {
            boundaries[i] = boundaries[i - 1] + population[i - 1].getFitness();
        }
        Random r = new Random();
        float random1 = r.nextFloat() * (boundaries[4]);
        int index = 1;
        while(boundaries[index] < random1) {
            index +=1;
        }
        return index;
    }

    private Gene[] findBest(List<Gene> tournament){
        Gene[] pair = new Gene[2];
        pair[0] = tournament.get(0);
        pair[1] = tournament.get(1);
        return pair;
    }

    private Gene[] genomCrossover(Gene parent1, Gene parent2) {
        System.out.println("Однородное вероятностное скрещивание");

        System.out.println("Геном 1-ого родителя: " + parent1);
        System.out.println("Геном 2-ого родителя: " + parent2);

        Gene[] result = new Gene[2];
        result[0] = new Gene();
        result[1] = new Gene();

        for (int i = 0; i < 5; i++) {
            if (Math.random() < parent1.getFitness()/(parent1.getFitness()+parent2.getFitness())) {
                result[0].getGenome()[i] = parent1.getGenome()[i];
                result[1].getGenome()[i] = parent2.getGenome()[i];
            } else {
                result[0].getGenome()[i] = parent2.getGenome()[i];
                result[1].getGenome()[i] = parent1.getGenome()[i];
            }
        }

        return result;
    }

    private Gene[] crossover(Gene[][] pairs) {
        Gene[] children = new Gene[10];
        Gene[] result;

        for (int i = 0; i < 10; i+=2) {
            Gene firstParent = pairs[i/2][0];
            Gene secondParent = pairs[i/2][1];

            result = genomCrossover(firstParent, secondParent);

            children[i] = result[0];
            children[i+1] = result[1];

            children[i] = children[i].mutation(0);
            children[i+1] = children[i+1].mutation(1);
        }

        System.out.println();

        return children;
    }

    public Gene[] getNewPopulation(Gene[][] pairs){
        Gene[] children  = crossover(pairs);
        Gene[] newPopulation = new Gene[5];
        int index = 0;

        for (int i =0; i < children.length; i++){
            float currentFitness = children[i].calculateFitness();
            children[i].setFitness(currentFitness);

            if (currentFitness == VALUE_ACHIEVED){
                newPopulation[index] = children[i];
                index++;
            }
        }

        float totalFitness = 0;
        index = 0;

        for (int i =0; i < children.length; i+=2) {
            totalFitness = children[i].getFitness() + children[i+1].getFitness() + population[index].getFitness();

            if (Math.random() < children[i].getFitness()/totalFitness)
                newPopulation[index] = children[i];
            else if (Math.random() < (children[i].getFitness() + children[i+1].getFitness())/totalFitness)
                newPopulation[index] = children[i+1];
            else if (Math.random() < 1)
                newPopulation[index] = population[index];
            index++;
        }

        for (int i = 0; i < newPopulation.length; i++){
            System.out.println(newPopulation[i].toString());
        }
        System.out.println("\n");

        return newPopulation;
    }

    public Gene[] getPopulation() {
        return population;
    }

    public void setPopulation(Gene[] population) {
        this.population = population;
    }
}