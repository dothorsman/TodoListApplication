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

     private static int nextID=1;

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

     public TodoItem(String title, String description, String duedate)
     {
          this.title = title;
          this.description = description;
          this.id = getNextID();
          this.status = false;
          this.creationTime = LocalDateTime.now();
          this.completionTime = null;
          this.deadlineTime = LocalDateTime.parse(duedate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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

     public static int getNextID()
     {
          int id=nextID;
          nextID++;
          return id;
     }

     //might need to change
     public void setIdToNextAvailable()
     {
          this.id = this.getNextID();
     }

     public boolean checkIfCompleted() {
          return status;
     }

     public void changeToIncomplete() {
          this.status = false;
     }

     public LocalDateTime getCreationTime() {
          return creationTime;
     }

     public void setCreationTime() { this.creationTime = LocalDateTime.now(); }

     public void setCreationTimeBy(String date) {
          this.creationTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }

     public LocalDateTime getCompletionTime() {
          return completionTime;
     }

     public LocalDateTime getDeadlineTime() {
          return deadlineTime;
     }

     public void snoozeDeadlineTime(String snoozeDate)
     {
          this.deadlineTime= LocalDateTime.parse(snoozeDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
     }

     public void completeItem()
     {
          this.completionTime = LocalDateTime.now();
          this.status = true;
     }

     public void setID(int id){
          this.id = id;
     }

     public void setStatus(boolean status){
          this.status=status;
     }



}
