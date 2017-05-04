package Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.ostaszewski on 04.05.2017.
 */
public class WindowsHardwareDetails extends HardwareDetails {

    public WindowsHardwareDetails(){
        super();
    }

    @Override
    public List<String> getCpu() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"cmd.exe", "/c", "wmic cpu get name"});
        List<String> foundedCPUs = new ArrayList<>();
        for(String line : commandOutputLines){
            if(!line.contains("Name") && !line.isEmpty()){
                foundedCPUs.add(line.trim());
            }
        }
        return foundedCPUs;
    }

    @Override
    public String getFormatedCpu() {
        return String.join(", ", getCpu());
    }

    @Override
    public List<String> getGpu() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"cmd.exe", "/c", "wmic path win32_VideoController get name"});
        List<String> foundedGPUs = new ArrayList<>();
        for(String line : commandOutputLines){
            if(!line.contains("Name") && !line.isEmpty()){
                foundedGPUs.add(line.trim());
            }
        }
        return foundedGPUs;
    }

    @Override
    public String getFormatedGpu() {
        return String.join(", ", getGpu());
    }

    @Override
    public List<String> getDisk() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"cmd.exe", "/c", "wmic diskdrive get model"});
        List<String> foundedDisks = new ArrayList<>();
        for(String line : commandOutputLines){
            if(!line.contains("Model") && !line.isEmpty()){
                foundedDisks.add(line.trim());
            }
        }
        return foundedDisks;
    }

    @Override
    public String getFormatedDisk() {
        return String.join(", ", getDisk());
    }

    @Override
    public List<String> getRam() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"cmd.exe", "/c", "wmic memorychip get manufacturer, capacity"});
        List<String> foundedDisks = new ArrayList<>();
        for(String line : commandOutputLines){
            if(!line.contains("Manufacturer") && !line.isEmpty()){
                foundedDisks.add(line.trim());
            }
        }
        return foundedDisks;
    }

    @Override
    public String getFormatedRam() {
        List<String> formatedLines = new ArrayList();
        for (String ramDetails : getRam()){
            String[] details = ramDetails.split("\\s+");
            System.out.println(details[0]);
            if(ramDetails.length() >=2) {
                long size = Long.parseLong(details[0]);
                String manufacturer = details[1];
                formatedLines.add(manufacturer + " " + size/1073741824 + "GB");
            }
        }
        return String.join(", ", formatedLines);
    }
}
