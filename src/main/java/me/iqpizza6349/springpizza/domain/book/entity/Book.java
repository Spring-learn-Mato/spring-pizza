package me.iqpizza6349.springpizza.domain.book.entity;

import lombok.*;
import me.iqpizza6349.springpizza.domain.library.entity.Library;
import me.iqpizza6349.springpizza.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Library library;

    @OneToOne
    @Setter
    private Member member;

    @Setter
    private boolean loan;

    @Column(name = "return_period")
    private LocalDate period;

    @Setter
    private boolean extension;

    public void setPeriod(LocalDate period) {
        if (extension) {
            // 이미 연장해다면, 더이상 연장 불가
            return;
        }

        this.period = period;
        extension = true;
    }
}
