public class Gene {
    private int[] genome = new int[5];
    private float fitness;

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int[] getGenome() {
        return genome;
    }

    public void setGenome(int[] genome) {
        this.genome = genome;
    }

    public static int getFunctionValue(int [] roots) {
        int[][]degrees = {
                //u w x y z
                {2,2,1,2,2},
                {0,1,1,0,1},
                {0,2,1,1,0},
                {0,0,0,0,1},
                {1,1,2,0,0}
        };
        //z + w * x*x * y*y * z*z + u*u * w*w * x * z + u*u * w * x * y * z*z + w*w * x*x * z*z
        int result = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                result+=degrees[i][j]*roots[j];
            }
        }
        return result;
    }

    public float calculateFitness() {
        int functionValue = -50;
        int fitness = Math.abs(functionValue- getFunctionValue(genome));
        System.out.println("Значение функции приспособленности: " + fitness);
        return 0 != fitness ? 1 / (float) fitness : Population.VALUE_ACHIEVED;
    }

    public Gene mutation(int childNumber) {
        System.out.println("Мутация генов в популяции:");
        Gene result = (Gene) this.cloneGenome();
        float mutableChance;
        if (childNumber==0) {
            mutableChance = 0;
        } else {
            mutableChance = 10F;
        }
        for (int i = 0; i < 5; i++) {
            float randomPercent = Handler.getRandomFloatInRange(0, 300);

            if (randomPercent < mutableChance) {
                int oldValue = result.getGenome()[i];
                int newValue = Population.createGenomeWithRandom();
                System.out.println("Мутация произошла в гене №" + i + ", старое значение = " + oldValue + ", новое значение = " + newValue);
                result.getGenome()[i] = newValue;
            }
        }

        System.out.println();
        return result;
    }

    private Object cloneGenome() {
        Gene resultGene = new Gene();

        resultGene.setFitness(this.getFitness());
        resultGene.setGenome(this.genome.clone());

        return resultGene;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");

        for (int i = 0; i < 5; ++i) {
            result.append(genome[i]);
            result.append(i < 4 ? ", " : "");
        }

        result.append(")");
        return result.toString();
    }
}