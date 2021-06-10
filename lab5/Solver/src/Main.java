public class Main {
    public static final int MAX_ITERATIONS = 500000;

    public static void main(String[] args) {
        Population population = new Population();
        population.createInitialPopulation();
        int iterationsNumber = 0;

        do {
            int fillingWithFitnesseResults = population.fillGenomesWithFitness();

            if (fillingWithFitnesseResults != Population.VALUE_NOT_ACHIEVED) {
                System.out.println("\nРешение уравнения: " + population.getPopulation()[fillingWithFitnesseResults]);
                int[] roots = population.getPopulation()[fillingWithFitnesseResults].getGenome();
                System.out.println("Проверка полученного значения функции: " + Gene.getFunctionValue(roots));
                return;
            }

            Handler.printAllGenomes(population);
            Gene[][] pairs = population.selection();
            Gene[] nextGeneration = population.getNewPopulation(pairs);
            population.setPopulation(nextGeneration);

            System.out.println("Конец " + iterationsNumber + "-ой итерации");
        } while (iterationsNumber++ < MAX_ITERATIONS);
    }
}
