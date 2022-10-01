package tn.scolarite.isi.model;

public class Task {

    private String id;
    private String name;
    private String date;
    public String taskDefinitionKey;

    public Task() {
    }

    public Task(String id, String name, String date, String taskDefinitionKey) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    @Override
    public String toString() {
        return
                " - " + id +
                " - \n" + name +
                " \nat : '" + date;
    }
}
