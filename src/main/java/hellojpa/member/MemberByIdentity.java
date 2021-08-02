package hellojpa.member;

import javax.persistence.*;

@Entity
public class MemberByIdentity {

    /*
    * @GeneratedValue OPTION
    * IDENTITY : creating keys according to each DB dialect
    * TABLE : create table for key, almost same as sequence strategy
    *   pros : can apply on every DB
    *   cons : performance issue
    * SEQUENCE : create sequence object
    *   cons : oracle only
    * AUTO : pick option
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /*
    ANNOTATION

    insertable, updateable > default value is true
    nullable > default value is true > false means null isn't allow to this column
    unique > unique annotation barely used because key naming rule doesn't clear
    */
    @Column(name = "name")
    private String username;

    public MemberByIdentity(){
    }

    public MemberByIdentity(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
