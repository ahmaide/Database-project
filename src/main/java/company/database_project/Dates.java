package company.database_project;

import java.util.Date;

public class Dates {

    public static String dateToday(){
        Date d = new Date();
        String month;
        String day;
        int tm = d.getMonth() + 1;
        if(tm <10)
            month = ("0" + tm);
        else
            month = String.valueOf((tm));
        if(d.getDate() <10)
            day = ("0" + d.getDate());
        else
            day = String.valueOf((d.getDate()));

        String s = Integer.toString(d.getYear() + 1900) + "=" + month + "=" + day;
        return s;
    }

    public static int dateInt(String d){
        int year = Integer.parseInt(d.substring(0,4));
        int month = Integer.parseInt(d.substring(5,7));
        int day = Integer.parseInt(d.substring(8,10));

        int date = year*10000 + month*100 + day;
        return date;
    }

    public static int stripDay(String date){
        int d = dateInt(date);
        return d/100;
    }

    public static String stringMonth(String date){
        int d = dateInt(date);
        int month = d/100;
        int m = month % 100;
        int year = month/100;
        String s;
        switch (m){
            case 1: {s=("Jan " + year); break;}
            case 2: {s=("Feb " + year); break;}
            case 3: {s=("Mar " + year); break;}
            case 4: {s=("Apr " + year); break;}
            case 5: {s=("May " + year); break;}
            case 6: {s=("Jun " + year); break;}
            case 7: {s=("Jul " + year); break;}
            case 8: {s=("Aug " + year); break;}
            case 9: {s=("Sep " + year); break;}
            case 10: {s=("Oct " + year); break;}
            case 11: {s=("Nov " + year); break;}
            case 12: {s=("Dec " + year); break;}
            default: s="";
        }
        return s;
    }

}
