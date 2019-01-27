import service.Finder;

public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Not all args written");
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        Finder finder = new Finder(inputFilePath, outputFilePath);
        try {
            finder.process();
        } finally {
            finder.dispose();
        }
    }
}
