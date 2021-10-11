package hellojpa.entity;

import hellojpa.entity.Item;

import javax.persistence.Entity;

@Entity
public class Movie extends Item {
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    private String director;
    private String actor;
}
