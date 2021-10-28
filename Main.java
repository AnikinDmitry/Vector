import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Working test
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 1, 1);
        Vector v3 = new Vector(1, 0, 1);
        Vector o = new Vector(3);
        Vector w1 = new Vector(v1);
        Vector e = new Vector();
        Vector e1 = new Vector(1, 0, 0);
        Vector e2 = new Vector(0, 1, 0);
        Vector e3 = new Vector(0, 0, 1);
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(v1);
        vectors.add(v2);
        vectors.add(v3);
        for (Vector vector : Vector.orthonormalization(vectors)) {
            System.out.println(vector);
        }
        System.out.println(v1.plus(v2).multiply(-1));
        System.out.println(v2.scalarProduct(v1));
        System.out.println(e.length());
        System.out.println(v1.reject(v1));
        System.out.println(v1.setCoordinates(5, 3.0));
        System.out.println(e1.vectorProduct(e2));
    }
}
