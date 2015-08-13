// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.params.KeyParameter;

public class DecryptFile
{

    public static int DATALEN = 256;
    public byte tbuffer[];

    public DecryptFile()
    {
        tbuffer = new byte[DATALEN];
    }

    private int getData(InputStream inputstream, byte abyte0[])
        throws IOException
    {
        int i = inputstream.read(tbuffer);
        if (i > 0)
        {
            if (i == DATALEN)
            {
                System.arraycopy(tbuffer, 0, abyte0, 0, i);
                return i;
            }
            System.arraycopy(tbuffer, 0, abyte0, 0, i);
            int j;
            do
            {
                int k = inputstream.read(tbuffer, 0, DATALEN - i);
                j = i;
                if (k <= 0)
                {
                    break;
                }
                System.arraycopy(tbuffer, 0, abyte0, i, k);
                j = i + k;
                i = j;
            } while (j != DATALEN);
            return j;
        } else
        {
            return -1;
        }
    }

    public void encryptDecrypt(String s, String s1, String s2)
    {
        KeyParameter keyparameter = new KeyParameter(s2.getBytes());
        s2 = new BufferedBlockCipher(new BlowfishEngine());
        s2.init(false, keyparameter);
        byte abyte0[];
        byte abyte1[];
        s = new FileInputStream(s);
        s1 = new FileOutputStream(s1);
        abyte0 = new byte[DATALEN];
        abyte1 = new byte[DATALEN * 2];
_L1:
         i = getData(s, abyte0);
        if (i <= 0)
        {
            break MISSING_BLOCK_LABEL_132;
        }
label0:
        {
            if (i % DATALEN != 0)
            {
                break label0;
            }
            s1.write(abyte1, 0, s2.processBytes(abyte0, 0, i, abyte1, 0));
        }
		/**          goto _L1 */
		   break _L1;
        try
        {
            s1.write(abyte0, 0, i);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return;
        }
          goto _L1 
	
        s.close();
        s1.flush();
        s1.close();
        return;
    }

}
