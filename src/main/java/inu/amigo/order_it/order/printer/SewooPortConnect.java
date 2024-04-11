package inu.amigo.order_it.order.printer;

import com.sewoo.thermal.jni.LKPOSTOT;

/**
 * 윈도우 환경에서만 사용가능!!
 */
public class SewooPortConnect {

    private final LKPOSTOT SWLib;

    public SewooPortConnect(LKPOSTOT SWLib) {
        this.SWLib = SWLib;
    }

    public boolean openByUsb() {
        String portName = "USB";
        Long baudRate = Long.parseLong("9600");

        long lResult = SWLib.OpenPort(portName, baudRate);

        if (lResult != 0) {
            System.out.println("Open Port Failed(" + lResult + ")");
            return false;
        }
        return true;
    }

    public boolean close() {
        long lResult = SWLib.ClosePort();

        if(lResult != 0) {
            System.out.println("Close Port Failed(" + lResult + ")");
            return false;
        }
        return true;
    }
}
