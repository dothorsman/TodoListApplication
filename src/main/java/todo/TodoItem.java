package todo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem
{
     @DatabaseField()
     private String title;

     @DatabaseField()
     private String owner = "team4";

     @DatabaseField()
     private String description;

     @DatabaseField(id = true)
     private int id;

     @DatabaseField()
     private boolean status;

     @DatabaseField(dataType=DataType.SERIALIZABLE)
     private LocalDateTime creationTime;

     @DatabaseField(dataType=DataType.SERIALIZABLE)
     private LocalDateTime completionTime;

     @DatabaseField(dataType=DataType.SERIALIZABLE)
     private LocalDateTime deadlineTime;

     public TodoItem(){

     }

     public TodoItem(String title, String description, String deadline, int id)
     {
          this.title = title;
          this.description = description;
          this.id = id;
          this.status = false;
          this.creationTime = LocalDateTime.now();
          this.completionTime = null;
          this.deadlineTime = LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }

     public String getTitle() {
          return title;
     }

     public void setTitle(String title) {
          this.title = title;
     }

     public String getOwner() {
          return owner;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public int getId() {
          return id;
     }

     public void setID(int id) { this.id = id; }

     public LocalDateTime getCreationTime() {
          return creationTime;
     }

     public void setCreationTime() { this.creationTime = LocalDateTime.now(); }

     public void setCreationTimeBy(String date) {
          this.creationTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }


     public LocalDateTime getDeadlineTime() {
          return deadlineTime;
     }

     public void setDeadlineTime(String newDeadline)
     {
          this.deadlineTime = LocalDateTime.parse(newDeadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }

     public void snoozeDeadlineTime(String snoozeDate)
     {
          this.deadlineTime = LocalDateTime.parse(snoozeDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }


     public void setStatus(boolean status){
          this.status=status;
     }

     public boolean checkIfCompleted() {
          return status;
     }

     public void changeToIncomplete() {
          this.status = false;
     }

     public void completeItem()
     {
          this.completionTime = LocalDateTime.now();
          this.status = true;
     }

     public LocalDateTime getCompletionTime() {
          return completionTime;
     }


     public void updateItem(String newTitle, String newDescription, boolean newStatus, String newDeadline)
     {
          this.setTitle(newTitle);
          this.setDescription(newDescription);
          this.setCreationTime();

          if(newStatus == false)
          {
               this.changeToIncomplete();
          }
          else
          {
               this.completeItem();
          }

          this.setDeadlineTime(newDeadline);
     }
}

