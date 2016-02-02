import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Opcode {

    public int ADDRESS;

    public String mnemonic_name;

    public String Operand1;

    public String Operand2;

    public byte mnemonic = -0x1;


    /*
    Opcode keeps track of it's address, relative to the memory.

    Operands and the Name of the Opcode are also stored here.


     */

    public int DirectAddress(String OP){
        if(OP.contains("[") && OP.contains("]")){
            //OP is a direct memory address...
            OP = OP.replace("[", "");
            OP = OP.replace("]", "");



            if(OP.contains("+") || OP.contains("-")){
                //We are doing some arithmetic

                //Evaluate the offset maths

                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                int RESULT = 0;
                try {
                    RESULT = Double.valueOf(engine.eval(OP).toString()).intValue();
                }catch (Exception e){e.printStackTrace();}
                return RESULT;
            }

            //Return Pure Address
            return Integer.decode(OP);

        }
        //We can do a -1 because addresses are unsigned
        return -1;
    }

    public byte GetAddress(int Address){
        /*
        Goes into the Segment Memory Space, and gets the byte
        at the address (single byte) and returns the byte
         */

        return CPU.DATA_SEGMENT_MEMORY[Address];
    }

}
