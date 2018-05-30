import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Decoder {
    private HMM hmm = new HMM();
    private String in = new String();

    Decoder(HMM hmm, String in){
        this.hmm = hmm;
        this.in = in;
    }

    public String decode() throws Exception{
        return hmm.runViterbi(in);
    }

    public static void main(String args[]) throws Exception{
        Reader r = new FileReader("/Users/klara/Downloads/lecture/download-05/casino.hmm");
        BufferedReader bufferedReader = new BufferedReader(r);
        HMM hmm = new HMM(bufferedReader);
        Decoder decoder = new Decoder(hmm, "666534215");
        String path = decoder.decode();
        System.out.println("666534215");
        System.out.println(path);
    }
}
