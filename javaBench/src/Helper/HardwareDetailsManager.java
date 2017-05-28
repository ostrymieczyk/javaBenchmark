package Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class HardwareDetailsManager {

    /**
     * @param command
     * @return
     */
    List<String> getCommandOutput(String[] command){
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

    /**
     * @return
     */
    public abstract String getFormattedCpuDetails();

    /**
     * @return
     */
    public abstract String getFormattedGpuDetails();

    /**
     * @return
     */
    public abstract String getFormattedDiskDetails();

    /**
     * @return
     */
    public abstract String getFormattedRamDetails();

    /**
     * @return
     */
    public abstract String getFormattedName();
}
