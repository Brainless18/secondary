//Dedicated to Goli

package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.Memory;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.gates.sequential.Clock;
import simulator.gates.sequential.flipflops.DFlipFlop;
import simulator.network.Link;
import simulator.wrapper.wrappers.RealDFlipFlop;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Sample {
    public static void main(String[] args) throws InterruptedException {
        Register pc = new Register();


        Memory instructionMemory = new Memory("Instruction Memory ");

        Link[] address = new Link[16];
        for (int i = 0 ; i<16 ; i++){
            address[i] = Simulator.falseLogic;
        }
        Link[] data = new Link[32];
        for (int i = 0 ; i<32 ; i++){
            data[i] = Simulator.trueLogic;
        }


        instructionMemory.addInput(Simulator.trueLogic);
        instructionMemory.addInput(address);
        instructionMemory.addInput(data);

        Clock c = new Clock("c", 100);

        for (int j = 0; j < 16; j++) {
            assert false;
            DFlipFlop d = new DFlipFlop("d"+ j, c.getOutput(0) , Simulator.falseLogic);
            pc.getFlipFlops().add(d);
        }

        instructionMemory.addInput(Simulator.falseLogic);
        instructionMemory.addInput(pc.dataToArray());

        RegisterFile registerFile = new RegisterFile("Register file" , "49X64");
        for (int i = 0 ; i < 32 ; i++) {

            registerFile.setRf(new HashMap<Integer, Register>());

            Register r = new Register();


            for (int j = 0; j < 32; j++) {


                DFlipFlop d = new DFlipFlop("d" + j, c.getOutput(0), Simulator.falseLogic);

                //System.out.println(d);

                r.getFlipFlops().add(d);

            }
            r.setClockValue(c.getOutput(0));
            registerFile.getRf().put(i, r);
        }

        ControlUnit controlUnit = new ControlUnit("Control Unit" , "6X9");

        controlUnit.addInput(instructionMemory.getOutput(0) , instructionMemory.getOutput(1) , instructionMemory.getOutput(2) , instructionMemory.getOutput(3) , instructionMemory.getOutput(4) , instructionMemory.getOutput(5) );

        //write register 5 mux 2X1 to choose between instruction lines going to reg#2 regfile
        MultiPlexer multiPlexer1 = new MultiPlexer("mux1" , "3X1" , instructionMemory.getOutput(11) , instructionMemory.getOutput(16) , controlUnit.getOutput(0));
        MultiPlexer multiPlexer2 = new MultiPlexer("mux2" , "3X1" , instructionMemory.getOutput(12) , instructionMemory.getOutput(17) , controlUnit.getOutput(0));
        MultiPlexer multiPlexer3 = new MultiPlexer("mux3" , "3X1" , instructionMemory.getOutput(13) , instructionMemory.getOutput(18) , controlUnit.getOutput(0));
        MultiPlexer multiPlexer4 = new MultiPlexer("mux4" , "3X1" , instructionMemory.getOutput(14) , instructionMemory.getOutput(19) , controlUnit.getOutput(0));
        MultiPlexer multiPlexer5 = new MultiPlexer("mux5" , "3X1" , instructionMemory.getOutput(15) , instructionMemory.getOutput(20) , controlUnit.getOutput(0));

        registerFile.addInput(c.getOutput(0) , controlUnit.getOutput(8) , instructionMemory.getInput(6) , instructionMemory.getOutput(7) , instructionMemory.getOutput(8) , instructionMemory.getOutput(9) , instructionMemory.getOutput(10) , instructionMemory.getOutput(11) , instructionMemory.getOutput(12) , instructionMemory.getOutput(13) , instructionMemory.getOutput(14) , instructionMemory.getOutput(15) , multiPlexer1.getOutput(0) , multiPlexer2.getOutput(0) , multiPlexer3.getOutput(0) , multiPlexer4.getOutput(0) , multiPlexer5.getOutput(0) );
        registerFile.addInput(data);




        Simulator.debugger.addTrackItem(instructionMemory  , controlUnit ,registerFile );
        Simulator.debugger.setDelay(1000);
        Simulator.circuit.startCircuit("FAST");
    }
}