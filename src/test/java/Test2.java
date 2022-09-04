public class Test2 {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(Boolean.class.getTypeName());
        System.out.println(Class.forName(Boolean.class.getTypeName()));
    }
}
