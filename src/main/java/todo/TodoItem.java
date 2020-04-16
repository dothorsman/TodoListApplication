package todo;

import java.time.LocalDateTime;

public class TodoItem
{
     private String title;
     private String owner = "team4";
     private String description;
     private int deadlineYear;
     private int deadlineMonth;
     private int deadlineDate;
     private int deadlineHour;
     private int deadlineMinute;

     private int id; //might need to be final again
     private static int nextID=1;
     private boolean status;

     private LocalDateTime creationTime;
     private LocalDateTime completionTime;
     private LocalDateTime deadlineTime;


     public TodoItem(String title, String description, int deadlineYear, int deadlineMonth, int deadlineDate, int deadlineHour, int deadlineMinute)
     {
          this.title = title;
          this.description = description;
          this.id = getNextID();
          this.status = false;
          this.creationTime = LocalDateTime.now();
          this.completionTime = null;
          this.deadlineTime = LocalDateTime.of(deadlineYear, deadlineMonth, deadlineDate, deadlineHour, deadlineMinute);
     }

     public int getDeadlineYear() {
          return deadlineYear;
     }

     public int getDeadlineMonth() {
          return deadlineMonth;
     }

     public int getDeadlineDate() {
          return deadlineDate;
     }

     public int getDeadlineHour() {
          return deadlineHour;
     }

     public int getDeadlineMinute() {
          return deadlineMinute;
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

     /*
     public void setOwner(String owner)
     {
          this.owner = owner;
     }
     */

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

     public LocalDateTime getCompletionTime() {
          return completionTime;
     }

     //maybe remove
     public void setCompletionTime(int year, int month, int date, int hour, int minute)
     {
          this.completionTime = LocalDateTime.of(year, month, date, hour, minute);
     }

     public LocalDateTime getDeadlineTime() {
          return deadlineTime;
     }

     public void setDeadlineTime(int year, int month, int date, int hour, int minute)
     {
          this.deadlineTime = LocalDateTime.of(year, month, date, hour, minute);
     }

     public void snoozeDeadlineTime(long yearsAdded, long monthsAdded, long daysAdded, long hoursAdded, long minutesAdded)
     {
          this.deadlineTime = this.deadlineTime.plusYears(yearsAdded);
          this.deadlineTime = this.deadlineTime.plusMonths(monthsAdded);
          this.deadlineTime = this.deadlineTime.plusDays(daysAdded);
          this.deadlineTime = this.deadlineTime.plusHours(hoursAdded);
          this.deadlineTime = this.deadlineTime.plusMinutes(minutesAdded);
     }

     public void completeItem()
     {
          this.completionTime = LocalDateTime.now();
          this.status = true;
     }

}
