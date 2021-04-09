package factory;

import services.LibraryService;
import services.MavenCentralLibraryService;
import services.NpmRegistryLibraryService;
import services.PypiLibraryService;

public class LibraryServiceFactory {

    public LibraryService createLibraryService(String provider) {
        switch (provider) {
            case "maven":
            case "gradle":
                return new MavenCentralLibraryService();
            case "npm":
                return new NpmRegistryLibraryService();
            case "pypi":
                return new PypiLibraryService();
            default:
                return null;
        }
    }
}
