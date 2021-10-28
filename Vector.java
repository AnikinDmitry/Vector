import java.util.ArrayList;

public class Vector {
    private final ArrayList<Double> coordinates = new ArrayList<>();

    //Creating a vector with specified coordinates
    public Vector(double... coordinates) {
        for (double coordinate : coordinates)
            this.coordinates.add(coordinate);
    }

    //Creating a zero vector
    public Vector(int dimension) {
        if (dimension < 0)
            throw new IllegalArgumentException("Dimensions are undefined");
        for (int i = 0; i < dimension; i++)
            coordinates.add(0.0);
    }

    //Create a random unit three-dimensional vector
    public Vector() {
        double angel = 2 * Math.PI * Math.random();
        double z = Math.random() * 2 - 1;
        double x = Math.sqrt(1 - z * z) * Math.cos(angel);
        double y = Math.sqrt(1 - z * z) * Math.sin(angel);
        coordinates.add(x);
        coordinates.add(y);
        coordinates.add(z);
    }

    //Create a copy of the vector
    public Vector(Vector vector) {
        coordinates.addAll(vector.coordinates);
    }

    //Euclidean norm vector length
    public double length() {
        return Math.sqrt(scalarProduct(this));
    }

    //Multiplication by number
    public Vector multiply(double number) {
        Vector vector = new Vector(coordinates.size());
        for (int i = 0; i < coordinates.size(); i++)
            vector.coordinates.set(i, number * coordinates.get(i));
        return vector;
    }

    //Addition of the vector "vector" to the original vector
    public Vector plus(Vector vector) {
        if (vector.coordinates.size() != coordinates.size())
            throw new IllegalArgumentException("Dimensions are not equal");
        Vector newVector = new Vector(0);
        for (int i = 0; i < vector.coordinates.size(); i++)
            newVector.coordinates.add(vector.coordinates.get(i) + coordinates.get(i));
        return newVector;
    }

    //Subtracting the vector "vector" from the original vector
    public Vector minus(Vector vector) {
        return plus(vector.multiply(-1.0));
    }

    //Scalar product of the original vector and vector "vector"
    public double scalarProduct(Vector vector) {
        if (vector.coordinates.size() != coordinates.size())
            throw new IllegalArgumentException("Dimensions are not equal");
        double scalarProduct = 0;
        for (int i = 0; i < coordinates.size(); i++)
            scalarProduct += coordinates.get(i) * vector.coordinates.get(i);
        return scalarProduct;
    }

    //Get coordinate with index "index"
    public double getCoordinates(int index) {
        if (index < 0 || index >= coordinates.size())
            throw new IllegalArgumentException("Coordinate not found");
        return coordinates.get(index);
    }

    //Set coordinate "coordinate" with index "index"
    public Vector setCoordinates(int index, double coordinate) {
        if (index < 0)
            throw new IllegalArgumentException("Coordinate not found");
        Vector vector = new Vector(0);
        if (index < coordinates.size()) {
            vector.coordinates.addAll(coordinates);
            vector.coordinates.set(index, coordinate);
            return vector;
        }
        vector.coordinates.addAll(coordinates);
        for (int i = coordinates.size(); i < index; i++)
            vector.coordinates.add(0.0);
        vector.coordinates.add(coordinate);
        return vector;
    }

    //Projection of the original vector on vector "vector"
    public Vector project(Vector vector) {
        if (vector.length() == 0)
            throw new IllegalArgumentException("Impossible to build vector");
        return vector.multiply(scalarProduct(vector) / scalarProduct(this));
    }

    //Projection onto the orthogonal complement of the vector "vector"
    public Vector reject(Vector vector) {
        return minus(project(vector));
    }

    //Vector product of the original vector and vector "vector"
    public Vector vectorProduct(Vector vector) {
        if (vector.coordinates.size() != coordinates.size())
            throw new IllegalArgumentException("Dimensions are not equal");
        if (coordinates.size() == 1)
            return new Vector(3);
        if (coordinates.size() == 2) {
            Vector newVector = new Vector(2);
            newVector.coordinates.add(coordinates.get(0) * vector.coordinates.get(1) -
                    coordinates.get(1) * vector.coordinates.get(0));
            return newVector;
        }
        if (coordinates.size() == 3) {
            Vector newVector = new Vector(0);
            newVector.coordinates.add(coordinates.get(1) * vector.coordinates.get(2) -
                    coordinates.get(2) * vector.coordinates.get(1));
            newVector.coordinates.add(-coordinates.get(0) * vector.coordinates.get(2) +
                    coordinates.get(2) * vector.coordinates.get(0));
            newVector.coordinates.add(coordinates.get(0) * vector.coordinates.get(1) -
                    coordinates.get(1) * vector.coordinates.get(0));
            return newVector;
        }
        throw new IllegalArgumentException("Cross Product cannot be performed");
    }

    //Creating orthogonalization system of vector
    public static ArrayList<Vector> orthogonalization(ArrayList<Vector> vectors) {
        ArrayList<Vector> orthogonalVectors = new ArrayList<>();
        for (Vector vector : vectors) {
            Vector resultVector = vector;
            for (Vector orthogonalVector : orthogonalVectors) {
                resultVector = resultVector.minus(resultVector.project(orthogonalVector));
            }
            if (resultVector.length() == 0)
                continue;
            orthogonalVectors.add(resultVector);
        }
        return orthogonalVectors;
    }

    //Creating orthonormalization system of vector
    public static ArrayList<Vector> orthonormalization(ArrayList<Vector> vectors) {
        ArrayList<Vector> orthonormalVectors = new ArrayList<>();
        for (int i = 0; i < Vector.orthogonalization(vectors).size(); i++)
            orthonormalVectors.add(Vector.orthogonalization(vectors).get(i).multiply(1 /
                    Vector.orthogonalization(vectors).get(i).length()));
        return orthonormalVectors;
    }

    //Column Vector
    public String toString() {
        StringBuilder vector = new StringBuilder();
        for (int i = 0; i < coordinates.size() - 1; i++)
            vector.append(coordinates.get(i)).append(" ");
        vector.append(coordinates.get(coordinates.size() - 1));
        return "(" + vector + ")T ";
    }
}