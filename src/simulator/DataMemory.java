package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Memory;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class DataMemory extends Wrapper {
    public DataMemory(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        // inputs : 16 bit address 32 bit data 1 bit memWrite 1 bit memRead
        Link readORWrite;
        if (getInput(32) == Simulator.trueLogic){
            readORWrite = Simulator.trueLogic;
        }
        else {
            readORWrite = Simulator.falseLogic;
        }
        Memory dataMemory = new Memory("datMemory" , readORWrite , getInput(0) , getInput(1) , getInput(2) , getInput(3) , getInput(4) , getInput(5) , getInput(6) , getInput(7) , getInput(8) , getInput(9) , getInput(10) , getInput(11)  , getInput(12) , getInput(13) , getInput(14) , getInput(15) , getInput(16) , getInput(17) , getInput(18) , getInput(19) , getInput(20) , getInput(21) , getInput(22) , getInput(23) , getInput(24) , getInput(25) , getInput(26) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0) , getInput(0));


        addOutput();
    }
}
