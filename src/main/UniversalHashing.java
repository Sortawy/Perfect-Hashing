package src.main;

public class UniversalHashing<T> {
    private int b;
    private int u;
    private int hashTableSize;
    private int[][] h;
    private int[][] x;
    private final int MAX_NUMBER_OF_BITS = 32;

    public UniversalHashing(int hashTableSize) {
        this.hashTableSize = hashTableSize;
        b = (int) Math.ceil(Math.log(hashTableSize) / Math.log(2));
        u = MAX_NUMBER_OF_BITS;
        h = new int[b][u];
        x = new int[u][1];
        generateRandomMatrix();
    }

    public int hash(T value) {
        fillVector(value.hashCode());
        String key = matrixMultiplication();
        return !key.isEmpty()? Integer.parseInt(key, 2) % hashTableSize : 0;
    }

    public void regenerateHashFunction() {
        generateRandomMatrix();
    }

    private void generateRandomMatrix() {
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < u; j++) {
                h[i][j] = (int) (Math.random() * 2);
            }
        }
    }

    private void fillVector(int value) {
        String binaryKey = Integer.toBinaryString(value);
        int j = binaryKey.length()-1;
        for (int i = MAX_NUMBER_OF_BITS - 1; i >= 0; i--) {
            char c = j >= 0 ? binaryKey.charAt(j--) : '0';
            x[i][0] =  Integer.parseInt(String.valueOf(c));
        }
    }

    private String matrixMultiplication() {
        int result = 0;
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < u; j++) {
                result += h[i][j] * x[j][0];
            }
            ans.append(result % 2);
            result = 0;
        }
        return ans.toString();
    }

}
