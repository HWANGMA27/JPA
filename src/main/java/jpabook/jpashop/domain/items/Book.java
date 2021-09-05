package jpabook.jpashop.domain.items;

import javax.persistence.Entity;

@Entity
public class Book extends Item {
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private String author;
    private String isbn;
}
