package org.prography.kagongsillok.place.domain;

import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "business_hour")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalTime open;
    private LocalTime close;

    public BusinessHour(final String dayOfWeek, final LocalTime open, final LocalTime close) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        this.open = open;
        this.close = close;
    }
}