package Helper;

import java.util.ArrayList;
import java.util.List;

public class LinuxHardwareDetailsManager extends HardwareDetailsManager {
    public List<String> getCpuDetails() {
        return getCommandOutput(new String[]{"sh", "-c", "inxi -C -c 0"});
    }

    @Override
    public String getFormattedCpuDetails() {
        List<String> commandOutputLines = getCpuDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "cpu:";
        commandOutputLines.forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()).trim());
            }
        });
        return String.join("; ", output);
    }

    public List<String> getGpuDetails() {
        return getCommandOutput(new String[]{"sh", "-c", "inxi -G -c 0"});
    }

    @Override
    public String getFormattedGpuDetails() {
        List<String> commandOutputLines = getGpuDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "card-";
        commandOutputLines.forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()+2).trim());
            }
        });
        return String.join("; ", output);
    }

    public List<String> getDiskDetails() {
        return getCommandOutput(new String[]{"sh", "-c", "inxi -D -c 0"});
    }

    @Override
    public String getFormattedDiskDetails() {
        List<String> commandOutputLines = getDiskDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "model:";
        commandOutputLines.forEach(line -> {
            if(line.toLowerCase().contains(lookingFor)){
                int index = line.toLowerCase().indexOf(lookingFor);
                output.add(line.substring(index + lookingFor.length()).trim());
            }
        });
        return String.join("; ", output);
    }

    public List<String> getRamDetails() {
        return getCommandOutput(new String[]{"sh", "-c", "inxi -I -c 0"});
    }

    @Override
    public String getFormattedRamDetails() {
        List<String> commandOutputLines = getRamDetails();
        List<String> output = new ArrayList<>();
        String lookingFor = "memory:";
        commandOutputLines.forEach(line -> {
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

    @Override
    public String getFormattedName() {
        return null;
    }
}
