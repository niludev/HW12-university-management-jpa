package ir.maktabsharif.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = Student.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student extends Person{

    public static final String TABLE_NAME = "students";
    public static final String STUDENT_CODE_COLUMN = "student_code";
    public static final String FIELD_OF_STUDY_COLUMN = "field_of_study";
    public static final String ENTRY_YEAR_COLUMN = "entry_year";

    @Column(name = STUDENT_CODE_COLUMN)
    private String studentCode;

    @Column(name = FIELD_OF_STUDY_COLUMN)
    private String fieldOfStudy;

    @Column(name = ENTRY_YEAR_COLUMN)
    private int entryYear;
}
