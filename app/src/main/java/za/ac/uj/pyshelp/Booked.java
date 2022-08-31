package za.ac.uj.pyshelp;

public class Booked {

    private String Date;
    private String Time;
    private String Campus_ID;
    private String Student_ID;

    public Booked() {
    }

    public Booked(String date, String time, String campus_ID, String student_ID) {
        this.Date = date;
        this.Time = time;
        this.Campus_ID = campus_ID;
        this.Student_ID = student_ID;
    }

    public String getDate() {

        return Date;
    }

    public void setDate(String date) {

        this.Date = date;
    }

    public String getTime() {

        return Time;
    }

    public void setTime(String time) {

        this.Time = time;
    }

    public String getCampus_ID() {

        return Campus_ID;
    }

    public void setCampus_ID(String campus_ID) {

        this.Campus_ID = campus_ID;
    }

    public String getStudent_ID() {

        return Student_ID;
    }

    public void setStudent_ID(String student_ID) {

        this.Student_ID = student_ID;
    }


}
