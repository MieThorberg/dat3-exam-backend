package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rules")
public class Rules {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "rule")
    private String rule;

    public Rules()
    {
    }

    public Rules(String rule)
    {
        this.rule = rule;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getRule()
    {
        return rule;
    }

    public void setRule(String rule)
    {
        this.rule = rule;
    }
}
