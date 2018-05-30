import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

public class Generator {
    HMM hmm = new HMM();
    Random rand = new Random();
    String seq = new String();
    ArrayList<Integer> states = new ArrayList<>();

    public void setHmm(HMM hmm){
        this.hmm = hmm;
    }

    public String getStates(){
        StringBuilder symbols = new StringBuilder();
        for(int i = 0; i < states.size() - 1; ++i){
            Character c = hmm.getStateName(states.get(i));
            symbols.append(c);
        }
        return symbols.toString();
    }

    public int getRandomSample(double[] probs, int num) throws ArrayIndexOutOfBoundsException{
        int out = -1;
        double[] sum = new double[num];
        double sum_v = 0.0;
        for(int i = 0; i < num; ++i){
            sum_v += probs[i];
            sum[i] = sum_v;
        }
        double sample = rand.nextDouble();
        for(int i = 0; i < num; ++i){
            if(sum[i] > sample){
                out = i;
                break;
            }
        }
        if(out == -1) {
            throw (new ArrayIndexOutOfBoundsException("Random sample failed"));
        }
        return out;
    }

    public int getNextState(int currStateIndex){

        int nextState = -1;
        int numStates = hmm.getNumStates();
        double[] currTransProbabilities = new double[numStates];
        for(int i = 0; i < numStates; ++i){
            currTransProbabilities[i] = hmm.getTransMatrix(currStateIndex, i);
        }
        int next_state_index = getRandomSample(currTransProbabilities, numStates);
        states.add(next_state_index);
        return next_state_index;
    }

    public char getEmission(int stateIndex) throws ArrayIndexOutOfBoundsException{

        if(stateIndex == 0){
            throw (new ArrayIndexOutOfBoundsException("no emission in start state possible"));
        }
        int num = hmm.getNumSymbols();
        double[] emmissionProbs = new double[num];
        for(int i = 0; i < num; ++i){
            emmissionProbs[i] = hmm.getEmissionProb(stateIndex, i);
        }
        int emm_index = getRandomSample(emmissionProbs, num);
        return hmm.getSymbolName(emm_index);
    }

    public void generate(){
        states.clear();
        StringBuilder seqBuilder = new StringBuilder();
        int current_state_index = 0;

        current_state_index = getNextState(current_state_index);
        while(current_state_index != 0){
            seqBuilder.append(getEmission(current_state_index));
            current_state_index = getNextState(current_state_index);
            //seqBuilder.append(getEmission(current_state_index));
        }
        seq = seqBuilder.toString();
    }

    public static void main(String[] args) throws Exception{
        Generator generator = new Generator();
        Reader r = new FileReader("/Users/klara/Downloads/lecture/download-05/casino.hmm");
        BufferedReader bufferedReader = new BufferedReader(r);
        HMM hmm = new HMM(bufferedReader);
        generator.setHmm(hmm);

        generator.generate();
        String sequence = generator.seq;
        System.out.println(sequence);
        hmm.runViterbi(sequence);
    }

}
