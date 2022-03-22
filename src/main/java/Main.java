import java.io.File;

public class Main {

    public static void main(String[] args) {
        String srcFolder = "/Users/savelijgevel/desktop/images";
        String dstFolder = "/Users/savelijgevel/desktop/dst";

        File srcDir = new File(srcFolder);

        int cores = 20;

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int arrayLength = files.length / cores;
        int dop = files.length % cores;

        for(int i = 0; i < cores; i++) {
            File[] copyFiles;
            if(i == cores - 1) {
                copyFiles = new File[arrayLength + dop];
                System.arraycopy(files, i * arrayLength, copyFiles, 0, arrayLength + dop);
            } else {
                copyFiles = new File[arrayLength];
                System.arraycopy(files, i * arrayLength, copyFiles, 0, arrayLength);
            }
            new Thread(new ImageResizer(copyFiles, 300, dstFolder)).start();
        }

        while(Thread.activeCount() != 2) {
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
