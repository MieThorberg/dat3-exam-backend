package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tenants")
@NamedQuery(name = "Tenant.deleteAllRows", query = "DELETE from Tenant")
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "job")
    private String job;

    public Tenant() {
    }

    public Tenant(String name, String phone, String job) {
        this.name = name;
        this.phone = phone;
        this.job = job;
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
}