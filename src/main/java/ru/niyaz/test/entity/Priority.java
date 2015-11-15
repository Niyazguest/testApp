package ru.niyaz.test.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 04.09.15.
 */

@Entity
@Table(name = "priority", schema = "public")
public class Priority {

    private Long priorityId;
    private String name;
    private List<Tasks> tasks;

    @Id
    @SequenceGenerator(name = "priority_seq", sequenceName = "priority_priority_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priority_seq")
    @Column(name = "priority_id")
    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}
