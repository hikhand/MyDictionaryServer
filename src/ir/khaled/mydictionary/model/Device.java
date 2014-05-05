package ir.khaled.mydictionary.model;

/**
 * Created by khaled.bakhtiari on 4/30/2014.
 */
public class Device {
    public int id;
    public String udk;

    /**
     * checks whether a device is valid or not
     * @param udk is the device key and used to find the device
     * @return true if device is valid else otherwise
     */
    public static boolean isDeviceValid(String udk) {
        return true;
    }
}
