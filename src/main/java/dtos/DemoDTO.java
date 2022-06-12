package dtos;

import entities.Demo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DemoDTO {
    private long id;
    private String name;

    public DemoDTO(Demo demo) {
        if(demo.getId() != 0)
            this.id = demo.getId();
        this.name = demo.getName();
    }

    public static Set<DemoDTO> getDemoDTOs(List<Demo> demos) {
        Set<DemoDTO> demoDTOS = new HashSet<>();
        demos.forEach(demo -> demoDTOS.add(new DemoDTO(demo)));
        return demoDTOS;
    }

    public static Set<DemoDTO> getDemoDTOs(Set<Demo> demos) {
        Set<DemoDTO> demoDTOS = new HashSet<>();
        demos.forEach(demo -> demoDTOS.add(new DemoDTO(demo)));
        return demoDTOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemoDTO demoDTO = (DemoDTO) o;
        return id == demoDTO.id && Objects.equals(name, demoDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "DemoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
