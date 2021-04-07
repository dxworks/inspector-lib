package dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency {
    private String name;
    private String version;
    private String provider;
    private String url;

    @Override
    public String toString() {
        return "Dependency{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", provider='" + provider + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
