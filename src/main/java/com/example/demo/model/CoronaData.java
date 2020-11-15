package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "koronaVeri")
public class CoronaData implements Comparable<CoronaData> {
    @Id
    private String id;

    private String il;
    private Date tarih;
    private Long vaka;
    private Long vefat;
    private Long taburcu;


    public Date getDateTime() {
        return tarih;
    }

    public void setDateTime(Date datetime) {
        this.tarih = datetime;
    }

    @Override
    public int compareTo(CoronaData o) {
        return getDateTime().compareTo(o.getDateTime());
    }

}
