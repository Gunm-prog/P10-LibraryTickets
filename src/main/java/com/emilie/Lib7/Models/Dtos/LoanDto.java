package com.emilie.Lib7.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private DateTime loanStartDate;
    private DateTime loanEndDate;
    private boolean extended;
    private boolean loanStatus;

}
