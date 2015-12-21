package com.company;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by eug on 20.12.15.
 */
public class Encrypter implements Runnable
{
    private ArrayBlockingQueue<EncItem> q;
    private Thread t;
    private String secKey = "SWCLabHWSKJCE";
    private SecretKey sk;
    private Cipher cipher;

    //private Cipher cipher = new Cipher();

    public Encrypter(ArrayBlockingQueue<EncItem> q, SecretKey sk) {
        this.q = q;
        this.sk = sk;
    }

    @Override
    public void run() {
        byte[] iv = cipherInit();
        //String encMethStr;
        byte[] msg;

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/eug/IdeaProjects/SWCHT/src/com/company/vfs/msgSource"));
            while((line = br.readLine()) != null)
            {
                try {
                    msg = cipher.doFinal(line.getBytes("UTF-8"));

                    EncItem i = new EncItem(msg, "DES", iv);
                    try {
                        q.put(i);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
                catch (Exception e)
                {

                }



                /*try {
                    Thread.sleep(50);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                    return;//breaking the cycle
                }*/
            }
        }
        catch (IOException e)
        {

        }
    }

    private byte[] cipherInit()
    {
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            return iv;
            //byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*private String encrypt(String text, String method, String keyEncMeth)
    {
        try {
            Cipher c = Cipher.getInstance(method, "SunJCE");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secKey.getBytes("UTF-8"), keyEncMeth));
            String encMsg = new String(c.doFinal(text.getBytes("UTF-8")));
            return encMsg;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }*/

    public void start()
    {
        if(t == null)
        {
            t = new Thread(this, "EncThread");
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
