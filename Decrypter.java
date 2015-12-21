package com.company;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by eug on 20.12.15.
 */
public class Decrypter implements Runnable
{
    private ArrayBlockingQueue<EncItem> q;
    private Thread t;
    private String secKey = "SWCLabHWSKJCE";
    private LinkedList<EncItem> encItems = new LinkedList<>();
    private SecretKey sk;
    private Cipher cipher;

    public Decrypter(ArrayBlockingQueue<EncItem> q, SecretKey sk) {
        this.q = q;
        this.sk = sk;
    }

    @Override
    public void run() {
            while (!q.isEmpty()) {
                try {
                    EncItem i = q.take();

                    cipherInit(i.getIv());
                    String decMsg = new String(cipher.doFinal(i.getEncMsg()), "UTF-8");

                    i.setDecMsg(decMsg);

                    System.out.println(i.toString());
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                catch (Exception e)
                {
                    e.getMessage();
                }

                try {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                    return;
                }
            }
    }

    private void cipherInit(byte[] iv)
    {
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sk, new IvParameterSpec(iv));
            //String plaintext = new String(cipher.doFinal(ciphertext), "UTF-8");
            //System.out.println(plaintext);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    /*private String decrypt(String text, String method)
    {
        try {
            Cipher c = Cipher.getInstance(method, "SunJCE");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secKey.getBytes("UTF-8"), "AES"));
            String encMsg = new String(c.doFinal(text.getBytes("UTF-8")));
            return encMsg;
        }
        catch (Exception e)
        {
        }
        return null;
    }*/

    public void start()
    {
        if(t == null)
        {
            t = new Thread(this, "DecThread");
            t.start();
        }
        try {
            t.join();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
