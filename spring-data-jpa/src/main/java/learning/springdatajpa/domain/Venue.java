package learning.springdatajpa.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private long id;

}
