package ec.fin.baustro.servicevu.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileHelper {
    private FileHelper(){
        super();
    }

    public static String readFile(String pPath) throws IOException {
        String data="";
        try {
            InputStream fin = FileHelper.class.getClassLoader().getResourceAsStream(pPath);
            data = readStream(fin);
            fin.close();
            return data;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return data;
    }

    public static String readStream(InputStream pIn) throws IOException {
        byte[] b = new byte[9999];
        String data = "";
        int car;
        do {
            car = pIn.read(b);
            if (car > 0) {
                data = data + new String(b, 0, car, "ISO-8859-1");
            }
        } while(car > 0);
        return data;
    }
}
