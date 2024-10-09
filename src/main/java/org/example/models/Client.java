package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.annotation.Column;
import org.example.annotation.Entity;
import org.example.annotation.Id;
import org.example.annotation.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "client")
public class Client {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
}
