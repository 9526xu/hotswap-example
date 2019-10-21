package com.andyxh;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2019/10/20
 */
public class HotSwapMain {
    public static void main(String[] args) {
        for (VirtualMachineDescriptor descriptor : VirtualMachine.list()){
            System.out.println(descriptor.id());
        }
    }
}
