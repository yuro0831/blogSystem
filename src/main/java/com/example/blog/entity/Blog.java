package com.example.blog.entity;

<<<<<<< Updated upstream
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{blog.title.notblank}")
    @Size(max = 255, message = "{blog.title.size}")
    private String title;

    @NotBlank(message = "{blog.content.notblank}")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "{blog.category.notnull}")
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
=======
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity // JPAエンティティとしてマーク
@Data // Lombokを利用してGetter/Setterを自動生成
@Table(name = "blogs") // データベーステーブル名を指定
public class Blog {

    @Id // 主キーを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーを自動生成
    private Integer id; // データベースの id に対応

    @Column(nullable = false, length = 255) // タイトルのカラム設定
    private String title; // タイトル

    @Column(nullable = false) // 内容のカラム設定
    private String content; // 内容

    @Column(name = "created_at", nullable = false, updatable = false) // 作成日時
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false) // 更新日時
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at") // 削除日時
    private LocalDateTime deletedAt;
>>>>>>> Stashed changes
}
