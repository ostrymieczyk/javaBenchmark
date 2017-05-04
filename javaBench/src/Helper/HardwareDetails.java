package Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.ostaszewski on 04.05.2017.
 */
public abstract class HardwareDetails {

    protected List<String> getCommandOutput(String[] command){
        List<String> commandOutput = new ArrayList<>();
        ProcessBuilder builder = new ProcessBuilder(
                command);
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) { break; }
            commandOutput.add(line);
        }
        return commandOutput;
    }

    public abstract List<String> getCpu();

    public abstract String getFormatedCpu();

    public abstract List<String> getGpu();

    public abstract String getFormatedGpu();

    public abstract List<String> getDisk();

    public abstract String getFormatedDisk();

    public abstract List<String> getRam();

    public abstract String getFormatedRam();

}
