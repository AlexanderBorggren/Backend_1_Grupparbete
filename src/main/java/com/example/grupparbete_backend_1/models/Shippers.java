package com.example.grupparbete_backend_1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)

public class Shippers {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;
    @JsonProperty("id")
    @Column(unique = true)
    private Long external_Shippers_Id;
    @NotNull
    @NotEmpty
    private String companyName;
    @NotNull
    @NotEmpty
    private String phone;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updatedate;
}

