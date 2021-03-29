package com.emilie.Lib7.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="loan")
@Data
@NoArgsConstructor
public class Loan implements Serializable {

 public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="loan_id")
    private Long loanId;

    @ManyToOne
    @JoinColumn(name="copy_id", nullable=false)
    private Copy copy;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="loan_date", nullable=false)
    private DateTime loanDate;

    @Column(name="loan_end_date")
    private DateTime loanEndDate;

    @Column(name="extended", nullable=false)
    private boolean extended;

    @Column(name="loan_status", nullable=false)
    private Boolean loanStatus;







}
