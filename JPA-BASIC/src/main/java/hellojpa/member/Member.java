package hellojpa.member;

import hellojpa.entity.BaseEntity;
import hellojpa.entity.RoleType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    public void setUsername(String username) {
        this.username = username;
    }

    /*
        ANNOTATION

        insertable, updateable > default value is true
        nullable > default value is true > false means null isn't allow to this column
        unique > unique annotation barely used because key naming rule doesn't clear
        */
    @Column(name = "name", insertable = true,
            updatable = true, nullable = false, unique = true)
    private String username;

    private Integer age;
    /*
    EnumType
    OPTION 1 : ORDINAL(default) > INTEGER
               insert order of enum data
    OPTION 2 : STRING
               insert enum data

    WARNING
    Using ORDINAL option
        when data order changed or insert extra enum data
        it can cause disaster.
    */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //support java8
    //using dateTime without annotation
    private LocalDateTime testLocalTime;
    //variable type is String then make column type as clob
    @Lob
    private String description;

    //not mapping with database table
    @Transient
    private  int temp;

    public Member(){
    }

    public Member(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getTestLocalTime() {
        return testLocalTime;
    }

    public void setTestLocalTime(LocalDateTime testLocalTime) {
        this.testLocalTime = testLocalTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
