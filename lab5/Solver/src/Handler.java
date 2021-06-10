public class Handler {
    public static void printAllGenomes(Population population) {
        System.out.println("Последнее состояние всех генов:");

        for (int i = 0; i < 5; ++i) {
            System.out.println("Ген " + i + ": " + population.getPopulation()[i].toString());
        }
    }

    public static float getRandomFloatInRange(float min, float max) {
        return (float) (Math.random() * max + min);
    }
}