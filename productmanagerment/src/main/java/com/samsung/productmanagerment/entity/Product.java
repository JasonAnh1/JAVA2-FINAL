package com.samsung.bookmanagerment.entity;

import com.samsung.bookmanagerment.entity.contants.BookStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends DateAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private long price;
    private long catalogId;
    private Long mediaId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "catalogId", referencedColumnName = "id", insertable = false, updatable = false)
    private Catalog catalog;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "mediaId", referencedColumnName = "id", insertable = false, updatable = false)
    private UploadFile media;
}
