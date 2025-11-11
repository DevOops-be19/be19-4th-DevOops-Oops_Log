package com.devoops.oopslog.achivement.query.dto;

import lombok.Data;

import java.util.List;

@Data
public class AchivementSummaryDTO {
    private List<OopsRecordCountDTO> OopsRecords;
    private List<OohRecordCountDTO> OohRecords;

    private int oopsCount;
    private int oohCount;

    private List<TagCountDTO> topOopsTags;
    private List<TagCountDTO> topOohTags;

    private List<FindYearMonthDTO> findYearOops;
    private List<FindYearMonthDTO> findYearOoh;
}
