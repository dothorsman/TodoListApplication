package todo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TodoItem {
     private String title;
     private String owner;
     private String content;
     private final int id;
     private static int nextID=1;
     private boolean status;
     private Calendar creationTime;
     private Calendar completionTime;
     private Calendar deadlineTime;


     public TodoItem(String title, String owner, String content, int year, int month, int date, int hour, int minute){
          this.title = title;
          this.owner = owner;
          this.content = content;
          this.id = getNextID();
          this.status = false;
          this.creationTime = Calendar.getInstance();

          Calendar InitialTime = Calendar.getInstance();
          InitialTime.set(0,0,0,0,0);
          this.completionTime=InitialTime;

          Calendar deadlineTime = Calendar.getInstance();
          deadlineTime.set(year, month-1, date, hour, minute);
          this.deadlineTime = deadlineTime;
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

     public void setOwner(String owner) {
          this.owner = owner;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public int getId() {
          return id;
     }

     public static int getNextID(){
          int id=nextID;
          nextID++;
          return id;
     }

     public boolean checkIfCompleted() {
          return status;
     }

     public void changeToCompleted() {
          this.status = true;
          setCompletionTime();
     }

     public void changeToIncomplete() {
          this.status = false;
     }

     public Calendar getCreationTime() {
          return creationTime;
     }

     public Calendar getCompletionTime() {
          return completionTime;
     }

     public void setCompletionTime() {
          Calendar cal=Calendar.getInstance();
          int year = cal.get(Calendar.YEAR);
          int month = cal.get(Calendar.MONTH);
          int day = cal.get(Calendar.DATE);
          int hour= cal.get(Calendar.HOUR);
          int min= cal.get(Calendar.MINUTE);
          this.completionTime.set(year,month,day,hour,min);
     }

     public Calendar getDeadlineTime() {
          return deadlineTime;
     }

}
