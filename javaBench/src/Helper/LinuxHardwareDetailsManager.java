package Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert on 07.05.17.
 */
public class LinuxHardwareDetailsManager extends HardwareDetailsManager {
    @Override
    public List<String> getCpuDetails() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"sh", "-c", "inxi -C -c 0"});
        return commandOutputLines;
    }

    @Override
    public String getFormatedCpuDetails() {
        List<String> commandOutputLines = getCpuDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "cpu:";
        commandOutputLines.stream().forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()).trim());
            }
        });
        return String.join("; ", output);
    }

    @Override
    public List<String> getGpuDetails() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"sh", "-c", "inxi -G -c 0"});
        return commandOutputLines;
    }

    @Override
    public String getFormatedGpuDetails() {
        List<String> commandOutputLines = getGpuDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "card-";
        commandOutputLines.stream().forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()+2).trim());
            }
        });
        return String.join("; ", output);
    }

    @Override
    public List<String> getDiskDetails() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"sh", "-c", "inxi -D -c 0"});
        return commandOutputLines;
    }

    @Override
    public String getFormatedDiskDetails() {
        List<String> commandOutputLines = getDiskDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "model:";
        commandOutputLines.stream().forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()).trim());
            }
        });
        return String.join("; ", output);
    }

    @Override
    public List<String> getRamDetails() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"sh", "-c", "inxi -I -c 0"});
        return commandOutputLines;
    }

    @Override
    public String getFormatedRamDetails() {
        List<String> commandOutputLines = getRamDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "memory:";
        commandOutputLines.stream().forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int indexFrom = line.toLowerCase().indexOf(lookingFor);
                String tempString = line.substring(indexFrom + lookingFor.length()).trim();
                indexFrom = tempString.indexOf("/");
                int indexTo = tempString.toLowerCase().indexOf("mb");
                output.add(tempString.substring(indexFrom + "/".length(), indexTo + "mb".length()).trim());
            }
        });
        return String.join("; ", output);
    }
}
