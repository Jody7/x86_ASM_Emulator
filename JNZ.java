
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class JNZ extends Opcode{

    /*
    The JNZ instruction is a Opcode

    Takes in 1 Operands.

    Capable of direct addressing into Memory Space!

    Jumps if ZF is not zero
     */

    public JNZ(int Address, String OP1) throws Exception{
        super.mnemonic_name = "JNZ";
        super.ADDRESS = Address;

        super.Operand1 = OP1;

        super.mnemonic = CPU.JNZ_ID;

    }


    public void Execute_Instruction(){
        try {
            String OP1 = super.Operand1;


            int OP1VAL = 0;

            if (isNumeric(OP1) == false) {
                int OP1_ADR = DirectAddress(OP1);
                if(OP1_ADR>=0){
                    OP1VAL = GetAddress(OP1_ADR);
                }else {
                    if(OP1.contains("x")){
                        //OP1VAL is a Hexadecimal
                        OP1VAL = Integer.decode(OP1);
                    }else {
                        OP1VAL = CPU.class.getField(OP1).getInt(null);
                    }
                }
            } else {
                OP1VAL = Integer.valueOf(OP1);
            }

            //Set EIP
            if(CPU.ZF == false){
                CPU.EIP = OP1VAL;
            }

            return;
        }catch (Exception e) { e.printStackTrace(); }

    }



    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
