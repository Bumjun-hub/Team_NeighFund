package org.project.neighfund.domain.fund;

import jakarta.persistence.*;
import lombok.*;
import org.project.neighfund.enums.CommunityCategory;
import org.project.neighfund.enums.FundStatus;
import org.project.neighfund.enums.FundType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FundType fundType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FundStatus fundStatus;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String subTitle;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;  //최대참여자

    @Column(name = "min_participants", nullable = false)
    private Integer minParticipants;  //최소참여자

    @Column(name = "current_participants", nullable = false)
    private Integer currentParticipants = 0;  //현재참여자

    @Column(name = "target_amount", nullable = false)
    private Integer targetAmount;  //목표금액

    @Column(name = "current_amount", nullable = false)
    private Integer currentAmount;  //현재 모인 금액

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;  //마감일





}
