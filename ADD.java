import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ADD extends Opcode{

    /*
    The ADD instruction is a Opcode

    Takes in 2 Operands.
     */

    public ADD(int Address, String OP1, String OP2) throws Exception{
        super.mnemonic_name = "ADD";
        super.ADDRESS = Address;

        super.Operand1 = OP1;
        super.Operand2 = OP2;

        super.mnemonic = CPU.ADD_ID;

    }


    public void Execute_Instruction(){
        try {
            String OP1 = super.Operand1;
            String OP2 = super.Operand2;

            int OP2VAL = 0;

            boolean OP1VAL_B = false;
            Field OP_FIELD = null;
            //Set up Data Extraction Vars

            if (isNumeric(OP1) == false) {
                OP_FIELD = CPU.class.getField(OP1);
                OP1VAL_B = true;
            }
            if (isNumeric(OP2) == false) {
                OP2VAL = CPU.class.getField(OP2).getInt(null);
            } else {
                OP2VAL = Integer.valueOf(OP2);
            }
            //If Operand is a register, extract int out of it.

            if (OP1VAL_B) {
                int SUM = OP2VAL + OP_FIELD.getInt(null);
                OP_FIELD.setInt(null, SUM);

                return;

            } else {
                //Invalid. Can not put value into a value...
                System.err.println("Instruction " + super.ADDRESS + ": error: invalid combination of opcode and operands");
                return;

            }
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
