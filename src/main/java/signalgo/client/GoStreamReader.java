package signalgo.client;

import signalgo.client.models.GoCompressMode;
import signalgo.client.models.GoDataType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by white on 2016-08-06.
 */
public class GoStreamReader {

    public byte[] readBlockToEnd(InputStream inputStream) throws IOException{
        int length=readSize(inputStream);
        byte[] data =read(inputStream,length);
        return data;

    }

    public boolean onTypeAuthenticationResponse(InputStream inputStream) throws IOException {
        byte[] response=read(inputStream,2);
        return true;
    }

    private int readSize(InputStream inputStream)throws IOException{
        byte[] size=read(inputStream,4);
        return ByteBuffer.wrap(size).order(ByteOrder.nativeOrder()).getInt();
    }

    private byte readByte(InputStream inputStream) throws IOException{
        return read(inputStream,1)[0];
    }

    public GoDataType readType(InputStream inputStream) throws IOException{
        byte type=readByte(inputStream);
        return GoDataType.getInstance(type);
    }

    public GoCompressMode readCompressMode(InputStream inputStream)throws IOException{
        byte mode=readByte(inputStream);
        return GoCompressMode.getInstance(mode);
    }

    private byte[] read(InputStream inputStream, int size) throws IOException{
        byte[] bytes=new byte[size];
        int readedLength=0;
        int remainingLength=size;
        int lastRead=0;
        
        while (readedLength<size) {
            int countToRead = Math.min(remainingLength, 2048);
            byte[] byteBuffer=new byte[countToRead];
            try{
            lastRead = inputStream.read(byteBuffer,0,countToRead);
            }catch(Exception e){
                Thread.currentThread().interrupt();
            }
            if (lastRead <= 0) {
                Thread.currentThread().interrupt();
                throw new IOException("readed stream size is " + lastRead);
            }
            bytes = byteAppendHelper(bytes, byteBuffer, lastRead,readedLength);
            readedLength+=lastRead;
            remainingLength -= lastRead;
        }
        return bytes;
    }


    private byte[] byteAppendHelper(byte[] dest,byte[] src,int lenght,int pos){
        /*List<Byte> bytes=new ArrayList<Byte>();

        byte[] b=bytes.toArray(byte[]  bytes1);*/

      //  byte[] result=new byte[dest.length];

        //System.arraycopy(dest,0,dest,0,dest.length);
        System.arraycopy(src,0,dest,pos,lenght);

        return dest;
    }

    private byte[] readHelper(InputStream inputStream,int len){
        byte[] result=new  byte[len];
        for (int i=0;i<len;i++){
            try {
                int t=inputStream.read(result,0,len);
                result[i]= (byte) t;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
