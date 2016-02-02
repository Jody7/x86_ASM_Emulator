public class CPU {

    //All Hexa ID's of each Opcode


    public static final int MOV_ID = 0x0;
    public static final int ADD_ID = 0x1;
    public static final int JMP_ID = 0x2;
    public static final int JNZ_ID = 0x3;


    public static byte DATA_SEGMENT_MEMORY[] = new byte[0xffffff];
    //2 MB of Mem-space

    //32 Bit Registers

    public static int EAX = 0x0;

    public static int EBX = 0x0;

    public static int ECX = 0x0;

    public static int EDX = 0x0;


    //Instruction Pointer

    public static int EIP = 0x0;

    //Flags

    public static boolean ZF = false;


    public static int INS_LEN = 0;
    //How many Instructions there are

    public static Opcode Instruction_Memory[];

    public static void main(String[] args){
        try {
            //Create our Instructions

            Instruction_Memory = new Opcode[]{
                    //Address, if we need to do something in the future
                new MOV(0x0, "[0x0]", "0"),
                new MOV(0x1, "EAX", "[0x0]"),
                new ADD(0x2, "EAX", "3"),
                new MOV(0x3, "[0x0]", "EAX"),
                new JMP(0x4, "0x1")

            };
            //32 Bits of Instruction Memory Space

            INS_LEN = Instruction_Memory.length;

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Begin the CPU Loop
        DRS(Instruction_Memory[EIP]);

        while(true) {
            if(EIP == INS_LEN) break;
            //No more Instructions to execute...

                switch (Instruction_Memory[EIP].mnemonic){
                    case 0x0: {
                        ((MOV) Instruction_Memory[EIP]).Execute_Instruction();

                        break;
                    }

                    case 0x1: {
                        ((ADD) Instruction_Memory[EIP]).Execute_Instruction();
                        break;
                    }

                    case 0x2: {
                        ((JMP) Instruction_Memory[EIP]).Execute_Instruction();
                        break;
                    }

                }
            DRS(Instruction_Memory[EIP]);
            MEM_DMP(0xF);

            //Increment Pointer to the next instruction.
            EIP++;
        }



    }

    public static void DRS(Opcode OP){
        //Display Register States
        System.out.println("------------------------");
        System.out.println("EIP : " + EIP + " --- " +  OP.mnemonic_name + " " + OP.Operand1 + ", " + OP.Operand2);
        System.out.println("");
        System.out.println(String.format("EAX   %08d", EAX));
        System.out.println(String.format("EBX   %08d", EBX));
        System.out.println(String.format("ECX   %08d", ECX));
        System.out.println(String.format("EDX   %08d", EDX));
        System.out.println("------------------------");
    }

    public static void MEM_DMP(double SIZE){
        //Dumps out the segment memory
        System.out.println("----MEM_DUMP----");
        int Counter = 0;
        int Counter_B = 0;
        for(int i = 0; i < Math.ceil(SIZE/4); i++) {

            System.out.print(String.format("%06X", Counter_B) + " | ");
            for (int j = 0; j < 4; j++) {
                String MEM_BYTE = Integer.toHexString((DATA_SEGMENT_MEMORY[Counter] & 0xFF));
                if(MEM_BYTE.length() == 1){
                    MEM_BYTE = "0" + MEM_BYTE;
                }

                System.out.print(MEM_BYTE + " ");
                Counter++;
            }
            Counter_B = Counter_B + 4;
            System.out.println();
        }
        System.out.println("\n --------------");
    }



}
