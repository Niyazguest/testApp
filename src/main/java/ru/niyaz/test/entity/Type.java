package ru.niyaz.test.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 04.09.15.
 */

@Entity
@Table(name = "type", schema = "public")
public class Type {
    private Long typeId;
    private String name;
    private List<Tasks> tasks;

    @Id
    @SequenceGenerator(name = "type_seq", sequenceName = "type_type_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_seq")
    @Column(name = "type_id")
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}
