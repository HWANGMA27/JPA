package hellojpa.member;

import javax.persistence.*;
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //sequence name
        initialValue = 1, allocationSize = 1)

@Entity
public class MemberBySeq {

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    /*
    ANNOTATION

    insertable, updateable > default value is true
    nullable > default value is true > false means null isn't allow to this column
    unique > unique annotation barely used because key naming rule doesn't clear
    */
    @Column(name = "name")
    private String username;

    public MemberBySeq(){
    }
}
