package com.company;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayBlockingQueue<EncItem> q = new ArrayBlockingQueue<EncItem>(30);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            KeySpec spec = new DESKeySpec("SWCHWSCG".getBytes());
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "DES");

            Encrypter e = new Encrypter(q, secret);
            Decrypter d = new Decrypter(q, secret);

            e.start();
            d.start();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
