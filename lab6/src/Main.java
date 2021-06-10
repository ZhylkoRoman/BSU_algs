public class Main {
    public static void main(String[] args) {
        String txt = "aaa 1    444bb  aa";
        int M = 2;
        KMP kmp = new KMP(txt, M);
        System.out.println(kmp.search());
        System.out.println(kmp.searchRepeats());
    }
}
