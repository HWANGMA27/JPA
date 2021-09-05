package jpabook.jpashop.domain;

import jpabook.jpashop.domain.items.BaseEntity;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String city;
    private String street;
    private String zipCode;
    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
