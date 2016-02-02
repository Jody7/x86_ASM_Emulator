
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MOV extends Opcode{

    /*
    The MOV instruction is a Opcode

    Takes in 2 Operands.

    Capable of direct addressing into Memory Space!
     */

    public MOV(int Address, String OP1, String OP2) throws Exception{
        super.mnemonic_name = "MOV";
        super.ADDRESS = Address;

        super.Operand1 = OP1;
        super.Operand2 = OP2;

        super.mnemonic = CPU.MOV_ID;

    }


    public void Execute_Instruction(){
        try {
            String OP1 = super.Operand1;
            String OP2 = super.Operand2;



            int OP2VAL = 0;

            boolean OP1VAL_B = false;
            boolean OP1_MEM = false;
            Field OP_FIELD = null;
            //Set up Data Extraction Vars

            if (isNumeric(OP1) == false) {

                if(DirectAddress(OP1)>=0){
                    //Let us know that OP1 is a Memory Address
                    OP1_MEM = true;

                }else {

                    OP_FIELD = CPU.class.getField(OP1);
                    OP1VAL_B = true;
                }
            }

            if (isNumeric(OP2) == false) {
                int OP2_ADR = DirectAddress(OP2);
                if(OP2_ADR>=0){
                    OP2VAL = GetAddress(OP2_ADR);
                }else {
                    if(OP2.contains("x")){
                        //OP2 is a Hexadecimal
                        OP2VAL = Integer.decode(OP2);
                    }else {
                        OP2VAL = CPU.class.getField(OP2).getInt(null);
                    }
                }
            } else {
                OP2VAL = Integer.valueOf(OP2);
            }
            //If Operand is a register, extract int out of it.

            if (OP1VAL_B) {
                //Operand 1 is a register.
                OP_FIELD.setInt(null, OP2VAL);

                return;

            } else {

                if(OP1_MEM){

                    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
                    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

                    byte[] FOUR_BYTES_INT = byteBuffer.putInt(OP2VAL).array();



                                             CPU.DATA_SEGMENT_MEMORY[DirectAddress(OP1)+ 0x0] = FOUR_BYTES_INT[0];
                    if(FOUR_BYTES_INT[0] > 0)CPU.DATA_SEGMENT_MEMORY[DirectAddress(OP1)+ 0x1] = FOUR_BYTES_INT[1];
                    if(FOUR_BYTES_INT[0] > 0)CPU.DATA_SEGMENT_MEMORY[DirectAddress(OP1)+ 0x2] = FOUR_BYTES_INT[2];
                    if(FOUR_BYTES_INT[0] > 0)CPU.DATA_SEGMENT_MEMORY[DirectAddress(OP1)+ 0x3] = FOUR_BYTES_INT[3];


                    //CPU.DATA_SEGMENT_MEMORY[DirectAddress(OP1)+ 0x0] = (byte) OP2VAL;


                    return;
                }
                //Invalid. Can not put value into a value...
                System.err.println("Instruction " + super.ADDRESS + ": error: invalid combination of opcode and operands");
                return;

            }
        }catch (Exception e) { e.printStackTrace(); }
        //Change the register inside of the MOV


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
