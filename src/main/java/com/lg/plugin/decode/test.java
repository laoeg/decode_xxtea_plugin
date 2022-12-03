package com.lg.plugin.decode;

import com.lg.plugin.decode.xxtea.XXTEA;

public class test {
    public static void main(String[] args) {
        String xVZS9feFCxki = new String(XXTEA.decryptBase64String("IfP25/9TDPmPyA6PFUMzTyUeAeUPRs8x", "k49lqyq7G6xU"));
        System.out.println("---------"+xVZS9feFCxki);
    }
}
