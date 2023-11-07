package com.reviews.Directory.entity_model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
//@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String businessName;
    private String email;
    private String mobile;

    private String physicalAddress;
    private String location;

    private String category;
    private String tinNumber;
    private String link;

    private String logoUrl;
//    private MultipartFile logo;  //Used for storing images locally. In BusinessDto

    @CreationTimestamp
    private Date createdAt = new Date();;
    @UpdateTimestamp
    private Date updatedAt = null;

    @OneToMany(mappedBy = "reviewSubject", fetch = FetchType.EAGER)
    private Set<Review> reviews;
    // Array is not good because fetching can cause errors.



/*

{
    "id": "2",
    "businessName":"ABC Limited",
    "email":"office@gmail.com",
    "mobile":"0712345678",
    "physicalAddress":"12 Faru Road",
    "location":"Mwenge",
    "category":"Technology",
    "tinNumber":"123456",
    "link":"www.instagram.com/abcltd",
    "created_at":"2023-07-24 10:10:20",
    "updated_at":"2023-07-27 10:10:20"
}
## Hash and equals override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Business business = (Business) o;
        return id != null && Objects.equals(id, business.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
*/
}
