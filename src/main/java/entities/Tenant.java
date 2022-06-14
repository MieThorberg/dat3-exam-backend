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

    @OneToOne
    @JoinColumn(name = "user_name")
    private User user;

    @Column(name = "phone")
    private String phone;

    @Column(name = "job")
    private String job;

    public Tenant() {
    }

    public Tenant(User user, String phone, String job) {
        this.user = user;
        this.phone = phone;
        this.job = job;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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