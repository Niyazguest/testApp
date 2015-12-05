package ru.niyaz.test.pojo;


/**
 * Created by user on 12.09.15.
 */
public class TaskObject {

    private Long id;
    private String name;
    private String definition;
    private String taskDate;
    private Long priorityId;
    private Long typeId;

    public TaskObject() {

    }

    public TaskObject(Long id, String name, String definition, String taskDate, Long priorityId, Long typeId) {
        this.id = id;
        this.name = name;
        this.definition = definition;
        this.taskDate = taskDate;
        this.priorityId = priorityId;
        this.typeId = typeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

}
