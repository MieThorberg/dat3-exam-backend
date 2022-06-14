package dtos;

import entities.House;
import entities.Tenant;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TenantDTO {
    private long id;
    private String name;
    private String phone;
    private String job;

    public TenantDTO(Tenant tenant) {
        if(tenant.getId() != 0)
            this.id = tenant.getId();
        this.name = tenant.getUser().getUserName();
        this.phone = tenant.getPhone();
        this.job = tenant.getPhone();
    }

    public static Set<TenantDTO> getTenantDTOs(List<Tenant> tenants) {
        Set<TenantDTO> tenantDTOS = new HashSet<>();
        tenants.forEach(tenant -> tenantDTOS.add(new TenantDTO(tenant)));
        return tenantDTOS;
    }

    public static Set<TenantDTO> getTenantDTOs(Set<Tenant> tenants) {
        Set<TenantDTO> tenantDTOS = new HashSet<>();
        tenants.forEach(tenant -> tenantDTOS.add(new TenantDTO(tenant)));
        return tenantDTOS;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantDTO tenantDTO = (TenantDTO) o;
        return id == tenantDTO.id && Objects.equals(name, tenantDTO.name) && Objects.equals(phone, tenantDTO.phone) && Objects.equals(job, tenantDTO.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, job);
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
