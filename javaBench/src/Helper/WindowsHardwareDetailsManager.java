package Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robert.ostaszewski on 04.05.2017.
 */
public class WindowsHardwareDetailsManager extends HardwareDetailsManager {

    public WindowsHardwareDetailsManager(){
        super();
    }

    @Override
    public List<String> getCpuDetails() {
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
    public String getFormatedCpuDetails() {
        return String.join("; ", getCpuDetails()).replaceAll(",", ";");
    }

    @Override
    public List<String> getGpuDetails() {
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
    public String getFormatedGpuDetails() {
        return String.join("; ", getGpuDetails());
    }

    @Override
    public List<String> getDiskDetails() {
        List<String> commandOutputLines = getCommandOutput(new String[]{"cmd.exe", "/c", "wmic diskdrive get model, size"});
        List<String> foundedDisks = new ArrayList<>();
        for(String line : commandOutputLines){
            if(!line.contains("Model") && !line.isEmpty()){
                foundedDisks.add(line.trim());
            }
        }
        return foundedDisks;
    }

    @Override
    public String getFormatedDiskDetails() {
        List<String> formatedLines = new ArrayList();
        for (String diskDetails : getDiskDetails()){
            List<String> details = Arrays.asList(diskDetails.split("\\s+"));
            if(details.size() >=2) {
                long size = Long.parseLong(details.get(details.size()-1));
                String manufacturer = String.join(" ", details.subList(0, details.size()-1));
                formatedLines.add(manufacturer + " " + size/(1024*1024*1024)+ "GB");
            }
        }
        return String.join("; ", formatedLines);
    }

    @Override
    public List<String> getRamDetails() {
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
    public String getFormatedRamDetails() {
        List<String> formatedLines = new ArrayList();
        for (String ramDetails : getRamDetails()){
            List<String> details = Arrays.asList(ramDetails.split("\\s+"));
            if(details.size() >=2) {
                long size = Long.parseLong(details.get(0));
                String manufacturer = String.join(" ", details.subList(1, details.size()));
                formatedLines.add(manufacturer + " " + size/(1024*1024*1024) + "GB");
            }
        }
        return String.join("; ", formatedLines);
    }
}
