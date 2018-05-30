import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class Evaluator {

    Generator gen = new Generator();
    HMM hmm = new HMM();
    Evaluator(HMM hmm){
        this.hmm = hmm;
        gen.setHmm(hmm);
    }

    public double evaluate(int num) throws Exception{

        int positives = 0;
        int total = 0;
        for(int i = 0; i < num; ++i){
            gen.generate();
            String genStates = gen.getStates();
            System.out.println("seq = "+gen.seq);
            String viterbiStates = hmm.runViterbi(gen.seq);
            System.out.println("genStates = "+genStates);
            System.out.println("viterbiStates = "+viterbiStates);
            int min_length = genStates.length();
            if(viterbiStates.length() < min_length){
                min_length = viterbiStates.length();
            }
            for(int k = 0; k < min_length; ++k) {
                if (genStates.charAt(k) == (viterbiStates.charAt(k))) {
                    positives++;
                }
                total ++;
            }
        }
        return (double)((double)positives / (double) total);
    }

    public static void main(String[] args) throws Exception{
        Reader r = new FileReader("/Users/klara/Downloads/lecture/download-05/casino.hmm");
        BufferedReader bufferedReader = new BufferedReader(r);
        HMM hmm = new HMM(bufferedReader);
        Evaluator evaluator = new Evaluator(hmm);
        double frac = evaluator.evaluate(100);
        System.out.println("probability viterbi is right = "+frac);

    }
}
