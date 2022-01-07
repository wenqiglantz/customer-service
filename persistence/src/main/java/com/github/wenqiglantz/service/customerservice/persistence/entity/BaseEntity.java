package com.github.wenqiglantz.service.customerservice.persistence.entity;

import lombok.Getter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "INSERTED_AT")
    private LocalDateTime insertedAt;

    @Column(name = "INSERTED_BY")
    private String insertedBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Should be implemented by subclass.");
    }


    @PrePersist
    private void onPrePersist() {
        id = UUID.randomUUID().toString();
        insertedAt = now();
        insertedBy = "System";
        updatedAt = insertedAt;
        updatedBy = insertedBy;
    }

    @PreUpdate
    private void onPreUpdate() {
        updatedAt = now();
        updatedBy = "System";
    }
}
