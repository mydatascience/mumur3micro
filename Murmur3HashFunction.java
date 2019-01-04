import java.io.DataInputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.nio.charset.Charset;

public final class Murmur3HashFunction {

   @SuppressWarnings("fallthrough")
  public static int murmurhash3_x86_32(byte[] data, int offset, int len, int seed) {

    final int c1 = 0xcc9e2d51;
    final int c2 = 0x1b873593;

    int h1 = seed;
    int roundedEnd = offset + (len & 0xfffffffc);  // round down to 4 byte block

    for (int i=offset; i<roundedEnd; i+=4) {
      // little endian load order
      int k1 = (data[i] & 0xff) | ((data[i+1] & 0xff) << 8) | ((data[i+2] & 0xff) << 16) | (data[i+3] << 24);
      k1 *= c1;
      k1 = Integer.rotateLeft(k1, 15);
      k1 *= c2;

      h1 ^= k1;
      h1 = Integer.rotateLeft(h1, 13);
      h1 = h1*5+0xe6546b64;
    }

    // tail
    int k1 = 0;

    switch(len & 0x03) {
      case 3:
        k1 = (data[roundedEnd + 2] & 0xff) << 16;
        // fallthrough
      case 2:
        k1 |= (data[roundedEnd + 1] & 0xff) << 8;
        // fallthrough
      case 1:
        k1 |= (data[roundedEnd] & 0xff);
        k1 *= c1;
        k1 = Integer.rotateLeft(k1, 15);
        k1 *= c2;
        h1 ^= k1;
    }

    // finalization
    h1 ^= len;

    // fmix(h1);
    h1 ^= h1 >>> 16;
    h1 *= 0x85ebca6b;
    h1 ^= h1 >>> 13;
    h1 *= 0xc2b2ae35;
    h1 ^= h1 >>> 16;

    return h1;
  }


    private Murmur3HashFunction() {
        //no instance
    }

    public static int hash(String routing) {
        final byte[] bytesToHash = new byte[routing.length() * 2];
        for (int i = 0; i < routing.length(); ++i) {
            final char c = routing.charAt(i);
            final byte b1 = (byte) c, b2 = (byte) (c >>> 8);
            assert ((b1 & 0xFF) | ((b2 & 0xFF) << 8)) == c; // no information loss
            bytesToHash[i * 2] = b1;
            bytesToHash[i * 2 + 1] = b2;
        }
        return hash(bytesToHash, 0, bytesToHash.length);
    }

    public static int hash(byte[] bytes, int offset, int length) {
        return murmurhash3_x86_32(bytes, offset, length, 0);
    }
    
     public static void main(String[] args) {
                int strlen = Integer. parseInt(args[0]);
                int cycles = Integer. parseInt(args[1]);		
                byte[] array = new byte[strlen]; // length is bounded by 7
                String[] myar=new String[cycles];
                for (int j=0;j<cycles;++j)
                {
                  new Random().nextBytes(array);
	        String generatedString = new String(array, Charset.forName("UTF-8"));
               // System.out.println(generatedString);
                 myar[j]=generatedString; 
                } 
                System.out.println("Run Murmurhash3!");
		long t1=java.lang.System.currentTimeMillis();
                for (int j=0;j<cycles;++j)
		{
		   hash(myar[j]);	
		}
		long t2=java.lang.System.currentTimeMillis();
		long t=t2-t1;
		System.out.println(t);
        }
		
}


