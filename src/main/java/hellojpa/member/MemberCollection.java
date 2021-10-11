package hellojpa.member;

import hellojpa.entity.Address;
import hellojpa.entity.AddressEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//class for study embedded type
@Entity
public class MemberCollection {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //address
    @Embedded
    private Address homeAdress;

    //Collection value's life cycle is depending on member's life cycle
    //Default : Lazy Loading
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFood = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    public MemberCollection() {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getHomeAdress() {
        return homeAdress;
    }

    public Set<String> getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(Set<String> favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public void setHomeAdress(Address homeAdress) {
        this.homeAdress = homeAdress;
    }
}
