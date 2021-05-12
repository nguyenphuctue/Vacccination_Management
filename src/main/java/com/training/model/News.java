package com.training.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "news_id", columnDefinition = "BINARY(16)")
    private UUID newsId;

    @Column(name = "content", columnDefinition = "NVARCHAR(4000)")
    private String content;

    @Column(name = "preview", columnDefinition = "NVARCHAR(1000)")
    private String preview;

    @Column(name = "title", columnDefinition = "NVARCHAR(300)")
    private String title;

    @Column(name = "post_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @Column(name = "active")
    private int active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "news_type_id")
    private NewsType newType;


}
